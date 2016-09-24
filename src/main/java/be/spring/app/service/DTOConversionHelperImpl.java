package be.spring.app.service;

import be.spring.app.dto.*;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                    convertGoals(m.getGoals(), isLoggedIn),
                    m.getHomeTeam().getAddress().getGoogleLink(),
                    m.getHomeTeam().getAddress().getAddress(),
                    m.getMatchDoodle() != null));
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
                    Integer.toString(match.getAtGoals()),
                    Integer.toString(match.getHtGoals()),
                    match.getStatus().name(),
                    convertMatchPoll(match.getMotmPoll(), isLoggedIn),
                    convertGoals(match.getGoals(), isLoggedIn),
                    match.getHomeTeam().getAddress().getGoogleLink(),
                    match.getHomeTeam().getAddress().getAddress(),
                    match.getMatchDoodle() != null);
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
    public MatchPollDTO convertMatchPoll(Match match, boolean isLoggedIn) {
        PlayersPoll playersPoll = match.getMotmPoll();
        if (playersPoll != null) {
            RankingList<Long> rankingList = playersPoll.getResult();
            return new MatchPollDTO(playersPoll.getId(),match.getId(),
                    convertIdentityRankings(rankingList, isLoggedIn),
                    convertIdentityOptions(playersPoll.getOptions(), isLoggedIn),
                    rankingList.getTotalVotes(), playersPoll.getStatus().name(), match.getDescription(), match.getStringDate());
        }
        return null;
    }

    @Override
    public MatchPollDTO convertMatchPoll(PlayersPoll playersPoll, boolean isLoggedIn) {
        if (playersPoll != null) {
            RankingList<Long> rankingList = playersPoll.getResult();
            return new MatchPollDTO(playersPoll.getId(),
                    convertIdentityRankings(rankingList, isLoggedIn),
                    convertIdentityOptions(playersPoll.getOptions(), isLoggedIn),
                    rankingList.getTotalVotes(), playersPoll.getStatus().name());
        }
        return null;
    }

    @Override
    public PageDTO<MatchPollDTO> convertMatchPolls(Page<Match> matches, boolean isLoggedIn) {
        List<MatchPollDTO> matchPollDTOMap = Lists.newLinkedList();
        for (Match m : matches.getContent()) {
            matchPollDTOMap.add(convertMatchPoll(m, isLoggedIn));
        }
        return new PageDTO<>(matchPollDTOMap, matches.getTotalPages(), matches.hasNext(), matches.hasPrevious());

    }

    @Override
    public List<AccountDTO> convertIdentityOptions(Set<IdentityOption> identityOptions, boolean isLoggedIn) {
        List<AccountDTO> accountDTOs = Lists.newArrayList();
        for (IdentityOption account : identityOptions) {
            accountDTOs.add(convertAccount(accountDao.findOne(account.getOption()), isLoggedIn));
        }
        Collections.sort(accountDTOs);
        return accountDTOs;
    }

    @Override
    public List<VotesDTO> convertIdentityRankings(RankingList<Long> rankingList, boolean isLoggedIn) {
        List<VotesDTO> votes = Lists.newArrayList();
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

    @Override
    public PageDTO<MatchDoodleDTO> convertMatchDoodles(Page<Match> match, Account account, boolean isAdmin) {
        List<MatchDoodleDTO> matchDoodleDTOList = Lists.newLinkedList();
        for (Match m : match.getContent()) {
            matchDoodleDTOList.add(convertMatchDoodle(m, account, isAdmin));
        }
        return new PageDTO<>(matchDoodleDTOList, match.getTotalPages(), match.hasNext(), match.hasPrevious());
    }

    @Override
    public MatchDoodleDTO convertMatchDoodle(Match match, Account account, boolean isAdmin) {
        return new MatchDoodleDTO(match.getId(),
                convertDoodle(match, account, isAdmin),
                match.getStringDate(),
                match.getDescription());
    }

    @Override
    public DoodleDTO convertDoodle(Match match, Account account, boolean isAdmin) {
        Doodle doodle = match.getMatchDoodle();
        if (doodle != null) {
            //Run though all active accounts, check if they are present and convert
            Set<PresenceDTO> presenceDTOs = Sets.newTreeSet();
            for (Account a : accountDao.findAllByActive(true)) {
                presenceDTOs.add(new PresenceDTO(convertAccount(a, account != null), doodle.isPresent(a),
                        isAdmin || (account != null && match.isActive() && a.equals(account))));
            }
            return new DoodleDTO(doodle.getId(),
                    presenceDTOs,
                    account == null ? null : new PresenceDTO(convertAccount(account, true), doodle.isPresent(account)
                            , match.isActive()),
                    doodle.countPresences());
        }
        return null;
    }

    @Override
    public PresenceDTO convertPresence(Presence presence, boolean isLoggedIn) {
        if (presence != null) {
            return new PresenceDTO(convertAccount(presence.getAccount(), isLoggedIn), presence.isPresent() ? Presence
                    .PresenceType.PRESENT : Presence.PresenceType.NOT_PRESENT, true);
        }
        return null;
    }

    @Override
    public PageDTO<NewsDTO> convertNewsPage(Account account, Page<News> page, boolean isAdmin) {
        List<NewsDTO> newsDTOList = Lists.newLinkedList();
        for (News n : page.getContent()) {
            newsDTOList.add(convertNews(account, n, isAdmin));
        }
        return new PageDTO<>(newsDTOList, page.getTotalPages(), page.hasNext(), page.hasPrevious() );
    }

    @Override
    public NewsDTO convertNews(Account account, News news, boolean isAdmin) {
        boolean isLoggedIn = account != null;
        if (news != null) {
            Set<CommentDTO> comments = Sets.newTreeSet();
            for (Comment comment : news.getComments()) {
                comments.add(convertComment(account, comment, isAdmin, isLoggedIn));
            }
            return new NewsDTO(news.getId(),
                    news.getHeader(),
                    news.getContent(),
                    news.getPostDateString(),
                    convertAccount(news.getAccount(), isLoggedIn),
                    isAdmin || (isLoggedIn && news.getAccount().equals(account)),
                    comments);
        }
        return null;
    }

    @Override
    public CommentDTO convertComment(Account account, Comment comment, boolean isAdmin, boolean isLoggedIn) {
        if (comment != null) {
            return new CommentDTO(comment.getId(), convertAccount(comment.getAccount(), isLoggedIn), comment.getContent(), comment.getPostDateString(),
                    isAdmin || (isLoggedIn && account.equals(comment.getAccount())));
        }
        return null;
    }
}
