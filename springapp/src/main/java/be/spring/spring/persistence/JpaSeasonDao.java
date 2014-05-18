package be.spring.spring.persistence;

import be.spring.spring.interfaces.SeasonDao;
import be.spring.spring.model.Season;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u0090265 on 5/13/14.
 */
@Repository
public class JpaSeasonDao extends AbstractJpaDao<Season> implements SeasonDao {
    @Override
    public List<Season> getLastSeasons(int max) {
        return getMultipleResultQuery("select s from Season s order by description desc", null);
    }

    @Override
    public List<Season> getAllSeasons() {
        return super.getAll();
    }
}
