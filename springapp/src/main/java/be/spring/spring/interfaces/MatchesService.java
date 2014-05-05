package be.spring.spring.interfaces;

import be.spring.spring.model.Match;

import java.util.List;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesService {
    Map<Integer, List<Match>> getMatchesForLastSeasons();
}
