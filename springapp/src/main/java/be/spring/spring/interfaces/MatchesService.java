package be.spring.spring.interfaces;

import be.spring.spring.form.CreateMatchForm;
import be.spring.spring.model.Match;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesService {
    Map<Integer, List<Match>> getMatchesForLastSeasons();

    List<Match> getMatchesForSeason(long seasonId);

    Match getMatch(Long id);

    boolean createMatch(CreateMatchForm form) throws ParseException;
}
