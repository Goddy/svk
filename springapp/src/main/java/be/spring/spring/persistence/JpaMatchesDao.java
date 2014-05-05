package be.spring.spring.persistence;

import be.spring.spring.interfaces.MatchesDao;
import be.spring.spring.model.Match;
import be.spring.spring.model.Season;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public class JpaMatchesDao extends AbstractJpaDao<Match> implements MatchesDao {
    @Override
    public List<Match> getMatchForSeason(Season season) {
        return null;
    }
}
