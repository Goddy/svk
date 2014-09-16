package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.ChangeResultForm;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.interfaces.*;
import be.spring.app.model.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ConcurrentDataService concurrentDataService;

    @Override
    public Map<Integer, List<Match>> getMatchesForLastSeasons() {
        int count = 1;
        Map<Integer, List<Match>> resultMap = new HashMap<>();
        for (Season season : seasonDao.getLastSeasons(maxSeasons)) {
            resultMap.put(count, matchesDao.getMatchForSeason(season));
            count++;
        }
        return resultMap;
    }

    @Override
    public List<ActionWrapper<Match>> getMatchesForSeason(String seasonId, final Account account, final Locale locale) {
        try {
            return concurrentDataService.getMatchForSeasonActionWrappers(seasonId, account, locale).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("getMatchesForSeason failed: {}", e.getMessage());
            return Lists.newArrayList();
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Match getMatch(String id) {
        return matchesDao.get(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Match createMatch(CreateMatchForm form) throws ParseException {
        Match m = new Match();
        m.setSeason(seasonDao.get(form.getSeason()));
        m.setDate(form.getDate());
        m.setHomeTeam(teamDao.get(form.getHomeTeam()));
        m.setAwayTeam(teamDao.get(form.getAwayTeam()));
        matchesDao.create(m);
        log.debug("Match {} created.", m);
        return m;
    }

    @Override
    @Transactional(readOnly = false)
    public Match updateMatchResult(ChangeResultForm form) {
        Match m = matchesDao.get(form.getMatchId());
        m.setGoals(transFormGoals(form));
        m.setAtGoals(form.getAtGoals());
        m.setHtGoals(form.getHtGoals());
        m.setPlayed(true);
        matchesDao.update(m);
        return m;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMatch(String id) throws ObjectNotFoundException {
        Match m = matchesDao.get(id);
        if (m == null) throw new ObjectNotFoundException(String.format("Match with id %s not found", id));
        matchesDao.delete(id);
    }

    private List<Goal> transFormGoals(ChangeResultForm form) {
        List<Goal> result = new ArrayList<>();
        for (ChangeResultForm.FormGoal goal : form.getGoals()) {
            Goal g = new Goal();
            g.setOrder(goal.getOrder());
            g.setScorer(accountDao.get(goal.getScorer()));
            g.setAssist(accountDao.get(goal.getAssist()));
            result.add(g);
        }
        return result;
    }
}
