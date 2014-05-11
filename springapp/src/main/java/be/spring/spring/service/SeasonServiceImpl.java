package be.spring.spring.service;

import be.spring.spring.interfaces.SeasonDao;
import be.spring.spring.interfaces.SeasonService;
import be.spring.spring.model.Season;
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
    public List<Season> getAllSeasons() {
        return seasonDao.getAll();
    }
}
