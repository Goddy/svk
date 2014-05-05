package be.spring.spring.persistence;

import be.spring.spring.interfaces.MatchesDao;
import be.spring.spring.model.Match;
import be.spring.spring.model.Season;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
@Repository
public class HbnMatchesDao extends AbstractHbnDao<Match> implements MatchesDao {

    @Override
    public List<Match> getMatchForSeason(Season season) {
        Query q = getSession().getNamedQuery("getMatchesForSeason");
        q.setParameter("season", season);
        return (List<Match>) q.list();
    }
}
