package be.spring.spring.service;

import be.spring.spring.controller.exceptions.ObjectNotFoundException;
import be.spring.spring.form.CreateAndUpdateTeamForm;
import be.spring.spring.interfaces.AddressDao;
import be.spring.spring.interfaces.MatchesDao;
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

    @Autowired
    private MatchesDao matchesDao;

    @Autowired
    private AddressDao addressDao;

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

    @Transactional(readOnly = false)
    @Override
    public Team createTeam(CreateAndUpdateTeamForm form) {
        Team team = new Team();
        updateTeamFromForm(form, team);
        teamDao.create(team);
        return team;
    }

    @Transactional(readOnly = false)
    @Override
    public Team updateTeam(CreateAndUpdateTeamForm form) {
        Team team = teamDao.get(form.getId());
        updateTeamFromForm(form, team);
        teamDao.update(team);
        return team;
    }

    private void updateTeamFromForm(CreateAndUpdateTeamForm form, Team team) {
        //If an existing address is chose, get the address, otherwise create a new one.
        if (form.isUseExistingAddress()) {
            Address address = addressDao.get(form.getAddressId());
            if (address == null) throw new ObjectNotFoundException(String.format("Address with id %s not found", form.getAddressId()));
            team.setAddress(address);
        } else {
            team.setAddress(getAddress(form));
        }
        team.setName(form.getTeamName());
    }

    private Address getAddress(CreateAndUpdateTeamForm form) {
        Address address = new Address();
        address.setAddress(form.getAddress());
        address.setCity(form.getCity());
        address.setPostalCode(Integer.parseInt(form.getPostalCode()));
        address.setGoogleLink(form.isUseLink() ? form.getGoogleLink() : null);
        return address;
    }

    @Override
    public List<ActionWrapper<Team>> getTeams(final Account account, final Locale locale) {
        List<Team> teams = teamDao.getAll();
        //Todo: make this OO, put an actionwrapper service in between, with handlers
        final List<ActionWrapper<Team>> actionWrappers = new ArrayList<>();

        for (Team t : teams) {
            actionWrappers.add(new ActionWrapper<>(t));
        }

        for (ActionWrapper<Team> teamActionWrapper : actionWrappers) {
            teamActionWrapper.setHtmlActions(htmlHelper.getTeamButtons(teamActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
        }

        /**teams.parallelStream().forEach(m -> actionWrappers.add(new ActionWrapper<>(m)));
        actionWrappers.parallelStream()
                .forEach(a -> a.setHtmlActions(htmlHelper.getTeamButtons(a.getObject(), securityUtils.isAdmin(account), locale)));**/
        return actionWrappers;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteTeam(String id, Account a) {
        Team team = teamDao.get(id);
        if (team == null) return true;
        if (matchesDao.isTeamInUse(team)) {
            return false;
        }
        else {
            teamDao.delete(team);
            return true;
        }
    }
}
