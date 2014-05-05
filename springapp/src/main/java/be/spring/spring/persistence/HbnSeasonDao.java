package be.spring.spring.persistence;

import be.spring.spring.interfaces.SeasonDao;
import be.spring.spring.model.Season;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
@Repository
public class HbnSeasonDao extends AbstractHbnDao<Season> implements SeasonDao {

    @Override
    public List<Season> getLastSeasons(int max) {
        Query q = getSession().getNamedQuery("getLastSeasons");
        q.setMaxResults(max);
        return (List<Season>) q.list();
    }
}
