package be.spring.spring.service;

import be.spring.spring.interfaces.TeamDao;
import be.spring.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by u0090265 on 5/10/14.
 */
@Service
public class TeamService {
    @Autowired
    private TeamDao teamDao;

    public List<Team> getAll() {
        return teamDao.getAll();
    }
}
