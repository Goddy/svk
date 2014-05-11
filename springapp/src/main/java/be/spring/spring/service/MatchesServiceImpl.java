package be.spring.spring.service;

import be.spring.spring.form.CreateMatchForm;
import be.spring.spring.interfaces.MatchesDao;
import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.interfaces.SeasonDao;
import be.spring.spring.interfaces.TeamDao;
import be.spring.spring.model.Match;
import be.spring.spring.model.Season;
import be.spring.spring.utils.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Season> getSeasons() {
        return seasonDao.getAllSeasons();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean createMatch(CreateMatchForm form) throws ParseException {
        Match m = new Match();
        m.setSeason(seasonDao.get((long) form.getSeason()));
        m.setDate(ValidationHelper.returnDate(form.getDate()));
        m.setHomeTeam(teamDao.get((long) form.getHomeTeam()));
        m.setAwayTeam(teamDao.get((long) form.getAwayTeam()));
        matchesDao.create(m);
        log.debug("Match {} created.", m);
        return true;
    }
}
