package be.spring.app.dto.helper;

import be.spring.app.dto.MatchDTO;
import be.spring.app.dto.TeamDTO;
import be.spring.app.model.Match;
import be.spring.app.model.Team;

import java.util.List;

/**
 * Created by u0090265 on 10/2/15.
 */
public interface ConversionHelper {
    List<MatchDTO> convertMatches(List<Match> matchList);

    List<TeamDTO> convertTeams(List<Team> teamList);
}
