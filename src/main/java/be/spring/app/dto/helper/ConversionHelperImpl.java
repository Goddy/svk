package be.spring.app.dto.helper;

import be.spring.app.dto.MatchDTO;
import be.spring.app.dto.TeamDTO;
import be.spring.app.model.Match;
import be.spring.app.model.Team;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by u0090265 on 10/2/15.
 */
@Component
public class ConversionHelperImpl implements ConversionHelper {

    @Override
    public List<MatchDTO> convertMatches(List<Match> matchList) {
        List<MatchDTO> matchDTOs = Lists.newArrayList();
        for (Match m : matchList) {
            matchDTOs.add(new MatchDTO(m.getStringDate(),
                    m.getStringHour(),
                    m.getHomeTeam().getName(),
                    m.getAwayTeam().getName(),
                    Integer.toString(m.getHtGoals()),
                    Integer.toString(m.getAtGoals())));
        }
        return matchDTOs;
    }

    @Override
    public List<TeamDTO> convertTeams(List<Team> teamList) {
        List<TeamDTO> teamDTOs = Lists.newArrayList();
        for (Team t : teamList) {
            teamDTOs.add(new TeamDTO(t.getName(),
                    String.format("%s,%s %s", t.getAddress().getAddress(), t.getAddress().getPostalCode(), t.getAddress().getCity()),
                    t.getAddress().getGoogleLink()));
        }
        return teamDTOs;
    }
}
