package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.data.MatchStatusEnum;
import be.spring.app.model.*;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.persistence.PollDao;
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
            if (match.getMotmPoll() == null) {
                PlayersPoll playersPoll = new PlayersPoll();
                Set<IdentityOption> players = Sets.newConcurrentHashSet();
                for (Presence p : match.getMatchDoodle().getPresences()) {
                    //Add if player was present
                    if (p.isPresent()) players.add(new IdentityOption(p.getAccount().getId(), playersPoll));
                }
                playersPoll.setOptions(players);
                playersPoll.setStartDate(DateTime.now());
                playersPoll.setEndDate(DateTime.now().plusDays(3));
                playersPoll.setQuestion("Automatically generated: Who will be man of the match?");
                match.setMotmPoll(playersPoll);
                return true;
            }
        }
        return false;
    }

    @Override
    public Vote vote(Long pollId, Vote vote) {
        //Get poll
        Poll poll = pollDao.findOne(pollId);
        if (poll == null) throw new ObjectNotFoundException(String.format("Poll %s not found", pollId));
        //Add vote to poll and make sure poll is added to vote
        vote.setPoll(poll);
        poll.getVotes().add(vote);
        pollDao.save(poll);
        return vote;
    }
}
