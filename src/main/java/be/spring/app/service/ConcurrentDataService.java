package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Match;
import be.spring.app.model.Team;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 9/12/14.
 */
public interface ConcurrentDataService {
    List<ActionWrapper<Match>> getMatchForSeasonActionWrappers(long seasonId, Account account, Locale locale);

    List<ActionWrapper<Team>> getTeamsActionWrappers(Account account, Locale locale);
}
