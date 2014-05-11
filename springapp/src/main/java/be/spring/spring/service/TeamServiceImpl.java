package be.spring.spring.service;

import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.interfaces.TeamDao;
import be.spring.spring.interfaces.TeamService;
import be.spring.spring.model.Address;
import be.spring.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by u0090265 on 5/10/14.
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamDao teamDao;

    @Override
    public List<Team> getAll() {
        return teamDao.getAll();
    }

    @Override
    public Team createTeam(CreateTeamForm form) {
        Team team = new Team();
        team.setAddress(getAddress(form));
        team.setName(form.getTeamName());
        teamDao.create(team);
        return team;
    }

    private Address getAddress(CreateTeamForm form) {
        Address address = new Address();
        address.setAddress(form.getAddress());
        address.setCity(form.getCity());
        address.setPostalCode(form.getPostalCode());
        address.setGoogleLink(form.getGoogleLink());
        return address;
    }
}
