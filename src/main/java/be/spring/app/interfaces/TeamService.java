package be.spring.app.interfaces;

import be.spring.app.form.CreateAndUpdateTeamForm;
import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Team;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 5/11/14.
 */
public interface TeamService {
    List<Team> getAll();

    boolean teamExists(String name);

    Team getTeam(String id);

    Team createTeam(CreateAndUpdateTeamForm form);

    Team updateTeam(CreateAndUpdateTeamForm form);

    List<ActionWrapper<Team>> getTeams(Account account, Locale locale);

    boolean deleteTeam(String id, Account a);
}
