package be.spring.app.service;

import be.spring.app.model.IdentityOption;
import be.spring.app.model.Match;
import be.spring.app.model.Poll;
import be.spring.app.model.Vote;

import java.util.Set;

/**
 * Created by u0090265 on 07/06/16.
 */
public interface PollService {
    boolean setMotmPoll(Match match);

    Set<IdentityOption> refreshPlayerOptions(Long id);

    Poll reset(Long id);

    Vote vote(Long pollId, Vote vote);
}
