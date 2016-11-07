package be.svk.webapp.service;

import be.svk.webapp.model.IdentityOption;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Poll;
import be.svk.webapp.model.Vote;

import java.util.Set;

/**
 * Created by u0090265 on 07/06/16.
 */
public interface PollService {
    boolean setMotmPoll(Match match);

    Poll get(Long pollId);

    Set<IdentityOption> refreshPlayerOptions(Long id);

    Poll reset(Long id);

    Poll vote(Long pollId, Vote vote);
}
