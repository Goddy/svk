package be.svk.webapp.service;

import be.svk.webapp.controller.exceptions.ObjectNotFoundException;
import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.dto.MatchDTO;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Season;
import be.svk.webapp.model.Team;
import be.svk.webapp.persistence.MatchesDao;
import be.svk.webapp.persistence.SeasonDao;
import be.svk.webapp.persistence.TeamDao;
import be.svk.webapp.utils.HtmlHelper;
import be.svk.webapp.utils.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by u0090265 on 9/12/14.
 */
@Service
public class ConcurrentDataServiceImpl implements ConcurrentDataService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private static final int N_THREADS = 20;

    private ListeningExecutorService executorService;

    private MatchesDao matchesDao;
    private TeamDao teamDao;
    private SeasonDao seasonDao;
    private HtmlHelper htmlHelper;
    private SecurityUtils securityUtils;
    private StatisticsService statisticsService;
    private DTOConversionHelper DTOConversionHelper;
    AccountService accountService;



    @Autowired
    public ConcurrentDataServiceImpl(MatchesDao matchesDao, TeamDao teamDao, SeasonDao seasonDao, SecurityUtils securityUtils, HtmlHelper htmlHelper, StatisticsService statisticsService, AccountService accountService, DTOConversionHelper DTOConversionHelper) {
        this.matchesDao = matchesDao;
        this.teamDao = teamDao;
        this.seasonDao = seasonDao;
        this.htmlHelper = htmlHelper;
        this.securityUtils = securityUtils;
        this.statisticsService = statisticsService;
        this.accountService = accountService;
        this.DTOConversionHelper = DTOConversionHelper;
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(N_THREADS));
    }

    @Override
    public ListenableFuture<List<AccountStatistic>> getAccountStatisticsForSeason(long seasonId) {
        Season season = seasonDao.findOne(seasonId);
        if (season == null) throw new ObjectNotFoundException(String.format("Season with id %s not found", seasonId));
        List<Match> matches = matchesDao.getMatchesForSeason(season);
        List<ListenableFuture<AccountStatistic>> stats = Lists.newArrayList();

        for (Account account : accountService.getAccountsByActivationStatus(true)) {
            stats.add(getAccountStatisticFor(matches, account));
        }
        return Futures.allAsList(stats);
    }

    private com.google.common.util.concurrent.ListenableFuture<AccountStatistic> getAccountStatisticFor(final List<Match> matches, final Account account) {
        return executorService.submit(new Callable<AccountStatistic>() {
            @Override
            public AccountStatistic call() throws Exception {
                return statisticsService.getAccountStatistic(matches, account);
            }
        });
    }

    //Todo: duplicated behaviour, should be avoided
    @Override
    public ListenableFuture<List<ActionWrapperDTO<MatchDTO>>> getMatchForSeasonActionWrappers(long seasonId, Locale locale, Account account) {
        Season season = seasonDao.findOne(seasonId);
        List<Match> matches = matchesDao.getMatchesForSeason(season);
        List<ListenableFuture<ActionWrapperDTO<MatchDTO>>> r = new ArrayList<>();

        for (Match m : matches) {
            r.add(prepareMatchDTO(account, m, locale));
        }
        return Futures.allAsList(r);
    }

    @Override
    public ListenableFuture<List<ActionWrapperDTO<Team>>> getTeamsActionWrappers(Account account, Locale locale) {
        List<Team> teams = Lists.newArrayList(teamDao.findAll());
        List<ListenableFuture<ActionWrapperDTO<Team>>> r = new ArrayList<>();

        for (ActionWrapperDTO<Team> teamActionWrapper : getActionWrappers(teams)) {
            r.add(setTeamHtmlActions(teamActionWrapper, locale, account));
        }
        /**
         Java 8 :( teams.parallelStream().forEach(m -> actionWrappers.add(new ActionWrapper<>(m)));
         actionWrappers.parallelStream()
         .forEach(a -> a.setHtmlActions(htmlHelper.getTeamButtons(a.getObject(), securityUtils.isAdmin(account), locale)));**/
        return Futures.allAsList(r);
    }

    private com.google.common.util.concurrent.ListenableFuture<ActionWrapperDTO<MatchDTO>> prepareMatchDTO(final Account account, final Match match, final Locale locale) {
        return executorService.submit(new Callable<ActionWrapperDTO<MatchDTO>>() {
            @Override
            public ActionWrapperDTO<MatchDTO> call() throws Exception {
                ActionWrapperDTO<MatchDTO> wrapperDTO = null;
                try {
                    wrapperDTO = new ActionWrapperDTO<>(DTOConversionHelper.convertMatch(match, account != null));
                    //Get account from security
                    //HashMap<String, String> map = new HashMap<>();
                    //map.putAll(htmlHelper.getMatchesButtons(match, securityUtils.isAdmin(account), locale));
                    //Todo: uncomment after finishing
                    //map.putAll(htmlHelper.getMatchesAdditions(match, account, locale));
                    //wrapperDTO.setAdditions(map);
                } catch (Exception e) {
                    log.error("Could not create Matches btns. Exception: {}", e.getMessage());
                    e.printStackTrace();
                }
                return wrapperDTO;
            }
        });

        /**
         *  return matches.parallelStream()
         .map(t -> prepareMatchDTO(new ActionWrapper<>(t), locale, account))
         .collect(Collectors.toList());
         */
    }

    private com.google.common.util.concurrent.ListenableFuture<ActionWrapperDTO<Team>> setTeamHtmlActions(final ActionWrapperDTO<Team> teamActionWrapper, final Locale locale, final Account account) {
        return executorService.submit(new Callable<ActionWrapperDTO<Team>>() {
            @Override
            public ActionWrapperDTO<Team> call() throws Exception {
                teamActionWrapper.setAdditions(htmlHelper.getTeamButtons(teamActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
                return teamActionWrapper;
            }
        });
    }

    private <T> List<ActionWrapperDTO<T>> getActionWrappers(List<T> objects) {
        final List<ActionWrapperDTO<T>> actionWrappers = new ArrayList<>();
        for (T o : objects) {
            actionWrappers.add(new ActionWrapperDTO<>(o));
        }
        return actionWrappers;
    }

}
