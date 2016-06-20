package be.spring.app.service;

import be.spring.app.model.Match;
import be.spring.app.model.Vote;

/**
 * Created by u0090265 on 07/06/16.
 */
public interface PollService {
    boolean setMotmPoll(Match match);

    Vote vote(Long pollId, Vote vote);
}
