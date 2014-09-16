package be.spring.app.persistence;

import be.spring.app.interfaces.TeamDao;
import be.spring.app.model.Team;
import org.springframework.stereotype.Repository;

/**
 * Created by u0090265 on 5/13/14.
 */
@Repository
public class JpaTeamDao extends AbstractJpaDao<Team> implements TeamDao {
    @Override
    public Team getTeamByName(String name) {
        return getSingleResultQuery("findTeamByName", getParameterMap("name", name));
    }
}
