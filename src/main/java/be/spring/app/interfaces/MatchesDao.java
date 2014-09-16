package be.spring.app.interfaces;

import be.spring.app.model.Match;
import be.spring.app.model.Season;
import be.spring.app.model.Team;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesDao extends Dao<Match> {
    List<Match> getMatchForSeason(Season season);

    boolean isTeamInUse(Team team);
}
