package be.spring.app.service;

import be.spring.app.dto.*;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by u0090265 on 10/2/15.
 */
@Service
public class DTOConversionHelperImpl implements DTOConversionHelper {
    @Autowired
    AccountDao accountDao;

    @Override
    public List<MatchDTO> convertMatches(List<Match> matchList, boolean isLoggedIn) {
        List<MatchDTO> matchDTOs = Lists.newArrayList();
        for (Match m : matchList) {
            matchDTOs.add(new MatchDTO(m.getId(),
                    m.getStringDate(),
                    m.getStringHour(),
                    m.getHomeTeam().getName(),
                    m.getAwayTeam().getName(),
                    Integer.toString(m.getHtGoals()),
                    Integer.toString(m.getAtGoals()),
                    m.getStatus().name(),
                    convertMatchPoll(m.getMotmPoll(), isLoggedIn),
                    convertGoals(m.getGoals(), isLoggedIn)));
        }
        return matchDTOs;
    }

    @Override
    public MatchDTO convertMatch(Match match, boolean isLoggedIn) {
        if (match != null) {
            return new MatchDTO(match.getId(),
                    match.getStringDate(),
                    match.getStringHour(),
                    match.getHomeTeam().getName(),
                    match.getAwayTeam().getName(),
                    Integer.toString(match.getHtGoals()),
                    Integer.toString(match.getAtGoals()),
                    match.getStatus().name(),
                    convertMatchPoll(match.getMotmPoll(), isLoggedIn),
                    convertGoals(match.getGoals(), isLoggedIn));
        }
        return null;
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

    @Override
    public MatchPollDTO convertMatchPoll(PlayersPoll playersPoll, boolean isLoggedIn) {
        if (playersPoll != null) {
            RankingList<Long> rankingList = playersPoll.getResult();
            return new MatchPollDTO(playersPoll.getId(),
                    convertIdentityRankings(rankingList, isLoggedIn),
                    convertIdentityOptions(playersPoll.getOptions(), isLoggedIn),
                    rankingList.getTotalVotes());
        }
        return null;
    }

    @Override
    public Set<AccountDTO> convertIdentityOptions(Set<IdentityOption> identityOptions, boolean isLoggedIn) {
        Set<AccountDTO> accountDTOs = Sets.newConcurrentHashSet();
        for (IdentityOption account : identityOptions) {
            accountDTOs.add(convertAccount(accountDao.findOne(account.getOption()), isLoggedIn));
        }
        return accountDTOs;
    }

    @Override
    public Set<VotesDTO> convertIdentityRankings(RankingList<Long> rankingList, boolean isLoggedIn) {
        Set<VotesDTO> votes = Sets.newConcurrentHashSet();
        for (Ranking<Long> ranking : rankingList.getRankings()) {
            votes.add(new VotesDTO(
                    convertAccount(accountDao.findOne(ranking.getOption()), isLoggedIn),
                    ranking.getPonts()));
        }
        return votes;
    }

    @Override
    public List<SeasonDTO> convertSeasons(List<Season> seasons) {
        List<SeasonDTO> list = Lists.newArrayList();
        for (Season s : seasons) {
            list.add(new SeasonDTO(s.getId(), s.getDescription()));
        }
        return list;
    }

    @Override
    public List<GoalDTO> convertGoals(SortedSet<Goal> goals, boolean isLoggedIn) {
        List<GoalDTO> result = Lists.newArrayList();
        for (Goal g : goals) {
            result.add(new GoalDTO(convertAccount(g.getScorer(), isLoggedIn), convertAccount(g.getAssist(), isLoggedIn)));
        }
        return result;
    }

    @Override
    public AccountDTO convertAccount(Account account, boolean isLoggedIn) {
        if (account != null) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(isLoggedIn ? account.toString() : account.getFullName());
            accountDTO.setId(account.getId());
            return accountDTO;
        }
        return null;
    }
}
