package be.spring.spring.persistence;

import be.spring.spring.interfaces.SeasonDao;
import be.spring.spring.model.Season;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public class HbnSeasonDao extends AbstractHbnDao<Season> implements SeasonDao {

    @Override
    public List<Season> getLastSeasons(int max) {
        Query q = getSession().getNamedQuery("getLastSeasons");
        q.setMaxResults(max);
        return (List<Season>) q.list();
    }

    @Override
    public List<Season> getAllSeasons() {
        Query q = getSession().getNamedQuery("getLastSeasons");
        return (List<Season>) q.list();
    }
}
