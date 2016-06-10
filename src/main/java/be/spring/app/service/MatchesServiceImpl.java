package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.data.MatchStatusEnum;
import be.spring.app.form.ChangeResultForm;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.persistence.SeasonDao;
import be.spring.app.persistence.TeamDao;
import be.spring.app.utils.Constants;
import be.spring.app.utils.GeneralUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by u0090265 on 5/3/14.
 */

@Service
public class MatchesServiceImpl implements MatchesService {

    private static final Logger log = LoggerFactory.getLogger(MatchesService.class);

    @Value("${max.seasons}")
    private int maxSeasons;

    @Autowired
    private SeasonDao seasonDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private MatchesDao matchesDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CacheAdapter cacheAdapter;

    @Autowired
    PollService pollService;

    @Override
    public Map<Integer, List<Match>> getMatchesForLastSeasons() {
        int count = 1;
        Map<Integer, List<Match>> resultMap = new HashMap<>();
        for (Season season : seasonDao.findAll(new PageRequest(0, maxSeasons, Sort.Direction.DESC, "description"))) {
            resultMap.put(count, matchesDao.getMatchesForSeason(season));
            count++;
        }
        return resultMap;
    }

    @Override
    public List<Match> getMatchesListForSeason(Season season) {
        return matchesDao.getMatchesForSeason(season);
    }

    @Override
    public Page<Match> getUpcomingMatchesPages(int start) {
        DateTime now = DateTime.now();
        return matchesDao.findByDateAfterOrderByDateAsc(now.minusDays(1), new PageRequest(start, Constants.TEN));
    }

    @Override
    public List<ActionWrapper<Match>> getMatchesWrappersForSeason(long seasonId, final Account account, final Locale locale) {
        try {
            return cacheAdapter.getAccountStatistics(seasonId, account, locale);
        } catch (InterruptedException | ExecutionException e) {
            log.error("getMatchesWrappersForSeason failed: {}", e.getMessage());
            return Lists.newArrayList();
        }
    }


    @Override
    public List<Match> getMatchesForSeason(long seasonId) {
        Season s = seasonDao.findOne(seasonId);
        if (s == null) throw new ObjectNotFoundException(String.format("Season with id %s not found", seasonId));
        return matchesDao.getMatchesForSeason(s);
    }

    @Override
    public List<Match> getMatchesForSeason(String description) {
        Season season = seasonDao.findByDescription(description);
        if (season == null) throw new ObjectNotFoundException(String.format("Season %s does not exists", description));
        return matchesDao.getMatchesForSeason(season);
    }

    @Override
    public Match getLatestMatch() {
        List<Match> matches = matchesDao.findByDate(DateTime.now());
        return matches.isEmpty() ? null : matchesDao.findByDate(DateTime.now()).get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public Match getMatch(long id) {
        return matchesDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Match createMatch(CreateMatchForm form) throws ParseException {
        Match m = new Match();
        m.setSeason(seasonDao.findOne(form.getSeason()));
        m.setDate(form.getDate());
        m.setHomeTeam(teamDao.findOne(form.getHomeTeam()));
        m.setAwayTeam(teamDao.findOne(form.getAwayTeam()));
        matchesDao.save(m);
        log.debug("Match {} created.", m);
        cacheAdapter.resetMatchCache();
        return m;
    }

    @Override
    @Transactional(readOnly = false)
    public Match updateMatch(ChangeResultForm form) {
        Match m = matchesDao.findOne(form.getMatchId());
        m.setHomeTeam(teamDao.findOne(form.getHomeTeam()));
        m.setAwayTeam(teamDao.findOne(form.getAwayTeam()));
        m.setDate(form.getDate());
        m.setSeason(seasonDao.findOne(form.getSeason()));

        //Get original match status
        MatchStatusEnum originalMatchStatus = m.getStatus();
        //Set matchstatus
        m.setStatus(form.getStatus());
        m.setStatusText(form.getStatus().equals(MatchStatusEnum.CANCELLED) ? form.getStatusText() : null);

        //If status has changed, check if the motm poll should be added
        if (!form.getStatus().equals(originalMatchStatus)) {
            pollService.setMotmPoll(m);
        }

        if (form.getStatus().equals(MatchStatusEnum.PLAYED)) {
            m.getGoals().clear();
            m.getGoals().addAll(transFormGoals(form, m));
            m.setAtGoals(form.getAtGoals());
            m.setHtGoals(form.getHtGoals());
        } else {
            m.getGoals().clear();
        }
        matchesDao.save(m);
        cacheAdapter.resetMatchCache();
        cacheAdapter.resetStatisticsCache();
        return m;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMatch(long id) throws ObjectNotFoundException {
        Match m = matchesDao.findOne(id);
        if (m == null) throw new ObjectNotFoundException(String.format("Match with id %s not found", id));
        matchesDao.delete(id);
        cacheAdapter.resetMatchCache();
    }

    private List<Goal> transFormGoals(ChangeResultForm form, Match match) {
        List<Goal> result = new ArrayList<>();
        for (ChangeResultForm.FormGoal goal : form.getGoals()) {
            Goal g = new Goal();
            g.setMatch(match);
            g.setOrder(goal.getOrder());
            //Goals and and assists can be null
            if (!Strings.isNullOrEmpty(goal.getScorer())) g.setScorer(accountDao.findOne(GeneralUtils.convertToLong(goal.getScorer())));
            if (!Strings.isNullOrEmpty(goal.getAssist())) g.setAssist(accountDao.findOne(GeneralUtils.convertToLong(goal.getAssist())));
            result.add(g);
        }
        return result;
    }
}
