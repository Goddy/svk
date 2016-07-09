package be.spring.app.dto.helper;

import be.spring.app.dto.*;
import be.spring.app.model.*;

import java.util.List;
import java.util.Set;

/**
 * Created by u0090265 on 10/2/15.
 */
public interface ConversionHelper {
    List<MatchDTO> convertMatches(List<Match> matchList);

    List<TeamDTO> convertTeams(List<Team> teamList);

    MatchPollDTO convertMatchPoll(PlayersPoll playersPoll, boolean isLoggedIn);

    Set<AccountDTO> convertIdentityOptions(Set<IdentityOption> identityOptions, boolean isLoggedIn);

    Set<VotesDTO> convertIdentityRankings(RankingList<Long> rankingList, boolean isLoggedIn);

    List<SeasonDTO> convertSeasons(List<Season> seasons);

    AccountDTO convertAccount(Account account, boolean isLoggedIn);
}
