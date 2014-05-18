package be.spring.spring.persistence;

import be.spring.spring.interfaces.TeamDao;
import be.spring.spring.model.Team;
import org.springframework.stereotype.Repository;

/**
 * Created by u0090265 on 5/13/14.
 */
@Repository
public class JpaTeamDao extends AbstractJpaDao<Team> implements TeamDao {
}
