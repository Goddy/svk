package be.spring.spring.service;

import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.model.Match;
import be.spring.spring.model.Season;
import be.spring.spring.persistence.HbnMatchesDao;
import be.spring.spring.persistence.HbnSeasonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */

@Service
public class MatchesServiceImpl implements MatchesService {

    @Value("${max.seasons}")
    private int maxSeasons;

    @Autowired
    private HbnSeasonDao seasonDao;

    @Autowired
    private HbnMatchesDao matchesDao;

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
}
