package be.spring.app.persistence;

import be.spring.app.interfaces.MatchesDao;
import be.spring.app.model.Match;
import be.spring.app.model.Season;
import be.spring.app.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
@Repository
public class JpaMatchesDao extends AbstractJpaDao<Match> implements MatchesDao {
    @Override
    public List<Match> getMatchForSeason(Season season) {
        return getMultipleResultQuery("getMatchesForSeason", getParameterMap("season", season));
    }

    @Override
    public boolean isTeamInUse(Team team) {
        return !getMultipleResultQuery("getMatchesForTeam", getParameterMap("team", team)).isEmpty();
    }
}
