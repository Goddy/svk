package be.spring.spring.service;

import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.interfaces.TeamDao;
import be.spring.spring.interfaces.TeamService;
import be.spring.spring.model.Account;
import be.spring.spring.model.ActionWrapper;
import be.spring.spring.model.Address;
import be.spring.spring.model.Team;
import be.spring.spring.utils.HtmlHelper;
import be.spring.spring.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 5/10/14.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private HtmlHelper htmlHelper;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public List<Team> getAll() {
        return teamDao.getAll();
    }

    @Override
    public boolean teamExists(String name) {
        return teamDao.getTeamByName(name) != null;
    }

    @Override
    public Team getTeam(String id) {
        return teamDao.get(id);
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
        address.setPostalCode(Integer.parseInt(form.getPostalCode()));
        address.setGoogleLink(form.getGoogleLink());
        return address;
    }

    @Override
    public List<ActionWrapper<Team>> getTeams(Account account, Locale locale) {
        List<Team> teams = teamDao.getAll();
        //Todo: make this OO, put an actionwrapper service in between, with handlers
        List<ActionWrapper<Team>> actionWrappers = new ArrayList<>();
        teams.parallelStream().forEach(m -> actionWrappers.add(new ActionWrapper<>(m)));
        actionWrappers.parallelStream()
                .forEach(a -> a.setHtmlActions(htmlHelper.getTeamButtons(a.getObject(), securityUtils.isAdmin(account), locale)));
        return actionWrappers;
    }

    @Override
    public boolean deleteTeam(String id, Account a) {
       try {
           deleteTeam(id);
           log.info(String.format("Team %s was deleted by %s", id, a));
           return true;
       }
       catch (Exception e) {
           log.error(String.format("Team %s could not be deleted by %s: %s", id, a, e.getMessage()));
           return false;
       }
    }

    @Transactional
    private void deleteTeam(String id) {
        teamDao.delete(id);
    }
}
