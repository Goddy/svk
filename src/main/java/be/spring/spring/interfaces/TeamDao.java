package be.spring.spring.interfaces;

import be.spring.spring.model.Team;

/**
 * Created by u0090265 on 5/10/14.
 */
public interface TeamDao extends Dao<Team> {
    public Team getTeamByName(String name);
}
