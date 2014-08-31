package be.spring.spring.interfaces;

import be.spring.spring.model.Match;
import be.spring.spring.model.Season;
import be.spring.spring.model.Team;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesDao extends Dao<Match> {
    List<Match> getMatchForSeason(Season season);

    boolean isTeamInUse(Team team);
}
