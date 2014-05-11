package be.spring.spring.interfaces;

import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.model.Team;

import java.util.List;

/**
 * Created by u0090265 on 5/11/14.
 */
public interface TeamService {
    List<Team> getAll();

    boolean teamExists(String name);

    Team createTeam(CreateTeamForm form);
}
