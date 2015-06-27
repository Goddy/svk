package be.spring.app.service;

import be.spring.app.model.Season;
import be.spring.app.persistence.SeasonDao;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by u0090265 on 5/11/14.
 */

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    SeasonDao seasonDao;

    @Override
    public List<Season> getSeasons() {
        return Lists.newArrayList(seasonDao.findAll());
    }

    @Override
    public Season getLatestSeason() {
        return Iterables.getLast(seasonDao.findAllOrderByDescriptionAsc(), null);
    }
}
