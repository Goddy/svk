package be.spring.app.service;

import be.spring.app.dto.*;
import be.spring.app.model.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by u0090265 on 10/2/15.
 */
public interface DTOConversionHelper {
    List<MatchDTO> convertMatches(List<Match> matchList, boolean isLoggedIn);

    MatchDTO convertMatch(Match match, boolean isLoggedIn);

    List<TeamDTO> convertTeams(List<Team> teamList);

    MatchPollDTO convertMatchPoll(PlayersPoll playersPoll, boolean isLoggedIn);

    MatchPollDTO convertMatchPoll(Match match, boolean isLoggedIn);

    PageDTO<MatchPollDTO> convertMatchPolls(Page<Match> matches, boolean isLoggedIn);

    List<AccountDTO> convertIdentityOptions(Set<IdentityOption> identityOptions, boolean isLoggedIn);

    List<VotesDTO> convertIdentityRankings(RankingList<Long> rankingList, boolean isLoggedIn);

    List<SeasonDTO> convertSeasons(List<Season> seasons);

    List<GoalDTO> convertGoals(SortedSet<Goal> goals, boolean isLoggedIn);

    AccountDTO convertAccount(Account account, boolean isLoggedIn);

    PageDTO<MatchDoodleDTO> convertMatchDoodles(Page<Match> match, Account account, boolean isAdmin);

    MatchDoodleDTO convertMatchDoodle(Match match, Account account, boolean isAdmin);

    DoodleDTO convertDoodle(Match match, Account account, boolean isAdmin);

    PresenceDTO convertPresence(Presence presence, boolean isLoggedIn);
}
