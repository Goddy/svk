package be.spring.app.service;

import be.spring.app.model.*;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.persistence.SeasonDao;
import be.spring.app.persistence.TeamDao;
import be.spring.app.utils.HtmlHelper;
import be.spring.app.utils.SecurityUtils;
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
import java.util.HashMap;
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


    @Autowired
    public ConcurrentDataServiceImpl(MatchesDao matchesDao, TeamDao teamDao, SeasonDao seasonDao, SecurityUtils securityUtils, HtmlHelper htmlHelper){
        this.matchesDao = matchesDao;
        this.teamDao = teamDao;
        this.seasonDao = seasonDao;
        this.htmlHelper = htmlHelper;
        this.securityUtils = securityUtils;
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(N_THREADS));
    }

    //Todo: duplicated behaviour, should be avoided
    @Override
    public ListenableFuture<List<ActionWrapper<Match>>> getMatchForSeasonActionWrappers(long seasonId, Account account, Locale locale) {
        Season season = seasonDao.findOne(seasonId);
        List<Match> matches = matchesDao.getMatchForSeason(season);
        List<ListenableFuture<ActionWrapper<Match>>> r = new ArrayList<>();

        for (ActionWrapper<Match> matchActionWrapper : getActionWrappers(matches)) {
            r.add(setMatchHtmlActions(matchActionWrapper, locale, account));
        }
        return Futures.allAsList(r);
    }

    @Override
    public  ListenableFuture<List<ActionWrapper<Team>>> getTeamsActionWrappers(Account account, Locale locale) {
        List<Team> teams = Lists.newArrayList(teamDao.findAll());
        List<ListenableFuture<ActionWrapper<Team>>> r = new ArrayList<>();

        for (ActionWrapper<Team> teamActionWrapper : getActionWrappers(teams)) {
            r.add(setTeamHtmlActions(teamActionWrapper, locale, account));
        }
        /**
         Java 8 :( teams.parallelStream().forEach(m -> actionWrappers.add(new ActionWrapper<>(m)));
         actionWrappers.parallelStream()
         .forEach(a -> a.setHtmlActions(htmlHelper.getTeamButtons(a.getObject(), securityUtils.isAdmin(account), locale)));**/
        return Futures.allAsList(r);
    }

    private com.google.common.util.concurrent.ListenableFuture<ActionWrapper<Match>> setMatchHtmlActions(final ActionWrapper<Match> matchActionWrapper, final Locale locale, final Account account) {
        return executorService.submit( new Callable<ActionWrapper<Match>>() {
            @Override
            public ActionWrapper<Match> call() throws Exception {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    map.putAll(htmlHelper.getMatchesButtons(matchActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
                    //Todo: uncomment after finishing
                    map.putAll(htmlHelper.getMatchesAdditions(matchActionWrapper.getObject(), account, locale));
                    matchActionWrapper.setAdditions(map);
                }
                catch (Exception e) {
                    log.error("Could not create Matches btns. Exception: {}", e.getMessage());
                    e.printStackTrace();
                }
                return matchActionWrapper;
            }
        });
    }

    private com.google.common.util.concurrent.ListenableFuture<ActionWrapper<Team>> setTeamHtmlActions(final ActionWrapper<Team> teamActionWrapper, final Locale locale, final Account account) {
        return executorService.submit( new Callable<ActionWrapper<Team>>() {
            @Override
            public ActionWrapper<Team> call() throws Exception {
                teamActionWrapper.setAdditions(htmlHelper.getTeamButtons(teamActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
                return teamActionWrapper;
            }
        });
    }

    private <T> List<ActionWrapper<T>> getActionWrappers(List<T> objects) {
        final List<ActionWrapper<T>> actionWrappers = new ArrayList<>();
        for (T o : objects) {
            actionWrappers.add(new ActionWrapper<>(o));
        }
        return actionWrappers;
    }

}
