package be.spring.app.dto.helper;

import be.spring.app.dto.AccountDTO;
import be.spring.app.dto.MatchDTO;
import be.spring.app.dto.MatchPollDTO;
import be.spring.app.dto.TeamDTO;
import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.model.PlayersPoll;
import be.spring.app.model.Team;

import java.util.List;

/**
 * Created by u0090265 on 10/2/15.
 */
public interface ConversionHelper {
    List<MatchDTO> convertMatches(List<Match> matchList);

    List<TeamDTO> convertTeams(List<Team> teamList);

    MatchPollDTO convertMatchPoll(PlayersPoll playersPoll, boolean isLoggedIn);

    AccountDTO convertAccount(Account account, boolean isLoggedIn);
}
