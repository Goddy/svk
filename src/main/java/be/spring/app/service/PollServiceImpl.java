package be.spring.app.service;

import be.spring.app.data.MatchStatusEnum;
import be.spring.app.model.*;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.persistence.PollDao;
import be.spring.app.utils.GeneralUtils;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by u0090265 on 07/06/16.
 */
@Service
public class PollServiceImpl implements PollService {
    @Autowired
    MatchesDao matchesDao;

    @Autowired
    PollDao pollDao;

    @Override
    public boolean setMotmPoll(Match match) {
        if (match.getStatus().equals(MatchStatusEnum.PLAYED)) {
            PlayersPoll playersPoll = new PlayersPoll();
            playersPoll.setOptions(getPlayerOptionsFor(match));
            playersPoll.setStartDate(DateTime.now());
            playersPoll.setEndDate(DateTime.now().plusDays(3));
            playersPoll.setQuestion("Automatically generated: Who will be man of the match?");
            match.setMotmPoll(playersPoll);
            return true;
        }
        return false;
    }

    @Override
    public Set<IdentityOption> refreshPlayerOptions(Long matchId) {
        Match match = matchesDao.findOne(matchId);
        GeneralUtils.throwObjectNotFoundException(match, matchId, Match.class);
        Set<IdentityOption> options = getPlayerOptionsFor(match);
        match.getMotmPoll().setOptions(options);
        matchesDao.save(match);
        return options;
    }

    @Override
    public Poll reset(Long id) {
        Poll poll = pollDao.findOne(id);
        GeneralUtils.throwObjectNotFoundException(poll, id, Poll.class);
        poll.getVotes().clear();
        return pollDao.save(poll);
    }

    @Override
    public Vote vote(Long pollId, Vote vote) {
        //Get poll
        Poll poll = pollDao.findOne(pollId);
        GeneralUtils.throwObjectNotFoundException(poll, pollId, Poll.class);
        //Add vote to poll and make sure poll is added to vote
        vote.setPoll(poll);
        poll.getVotes().add(vote);
        pollDao.save(poll);
        return vote;
    }

    private Set<IdentityOption> getPlayerOptionsFor(Match match) {
        Set<IdentityOption> options = Sets.newConcurrentHashSet();
        if (match.getMotmPoll() != null) {
            for (Presence p : match.getMatchDoodle().getPresences()) {
                //Add if player was present
                if (p.isPresent()) options.add(new IdentityOption(p.getAccount().getId(), match.getMotmPoll()));
            }
        }
        return options;
    }
}
