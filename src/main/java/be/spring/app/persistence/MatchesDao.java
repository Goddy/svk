package be.spring.app.persistence;

import be.spring.app.model.Match;
import be.spring.app.model.Season;
import be.spring.app.model.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesDao extends PagingAndSortingRepository<Match, Long>, JpaSpecificationExecutor<Match> {

    @Query("select m from Match m where m.season = ?1 order by date asc")
    List<Match> getMatchForSeason(Season season);

    @Query("select m from Match m where m.homeTeam = ?1 OR awayTeam = ?1 order by date desc")
    List<Match> getMatchesForTeam(Team team);
}