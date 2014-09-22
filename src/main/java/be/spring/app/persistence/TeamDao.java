package be.spring.app.persistence;

import be.spring.app.model.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by u0090265 on 5/10/14.
 */
public interface TeamDao extends PagingAndSortingRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    @Query("select t from Team t where t.name = ?1")
    public Team getTeamByName(String name);
}
