package be.spring.spring.service;

import be.spring.spring.form.ChangeResultForm;
import be.spring.spring.form.CreateMatchForm;
import be.spring.spring.interfaces.*;
import be.spring.spring.model.*;
import be.spring.spring.utils.HtmlHelper;
import be.spring.spring.utils.SecurityUtils;
import be.spring.spring.utils.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTML;
import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;

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
    private HtmlHelper htmlHelper;

    @Autowired
    private SecurityUtils securityUtils;

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
    public List<ActionWrapper<Match>> getMatchesForSeason(String seasonId, Account account, Locale locale) {
        Season season = seasonDao.get(seasonId);
        List<Match> matches = matchesDao.getMatchForSeason(season);
        List<ActionWrapper<Match>> actionWrappers = new ArrayList<>();
        matches.parallelStream().forEach(m -> actionWrappers.add(new ActionWrapper<>(m)));
        actionWrappers.parallelStream()
                .forEach(a -> a.setHtmlActions(htmlHelper.getMatchesButtons(a.getObject(), securityUtils.isAdmin(account), locale)));
        return actionWrappers;
    }

    @Override
    public Match getMatch(Long id) {
        return matchesDao.get(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean createMatch(CreateMatchForm form) throws ParseException {
        Match m = new Match();
        m.setSeason(seasonDao.get(form.getSeason()));
        m.setDate(form.getDate());
        m.setHomeTeam(teamDao.get(form.getHomeTeam()));
        m.setAwayTeam(teamDao.get(form.getAwayTeam()));
        matchesDao.create(m);
        log.debug("Match {} created.", m);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public Match updateMatchResult(ChangeResultForm form) {
        Match m = matchesDao.get(form.getMatchId());
        m.setGoals(transFormGoals(form));
        m.setAtGoals(form.getAtGoals());
        m.setHtGoals(form.getHtGoals());
        matchesDao.update(m);
        return m;
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
