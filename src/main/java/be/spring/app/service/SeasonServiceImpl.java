package be.spring.app.service;

import be.spring.app.interfaces.SeasonDao;
import be.spring.app.interfaces.SeasonService;
import be.spring.app.model.Season;
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
        return seasonDao.getAllSeasons();
    }
}
