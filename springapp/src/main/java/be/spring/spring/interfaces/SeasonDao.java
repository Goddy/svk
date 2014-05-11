package be.spring.spring.interfaces;

import be.spring.spring.model.Season;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface SeasonDao extends Dao<Season> {
    List<Season> getLastSeasons(int max);

    List<Season> getAllSeasons();
}
