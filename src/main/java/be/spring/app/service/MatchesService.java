package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.ChangeResultForm;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Match;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesService {
    Map<Integer, List<Match>> getMatchesForLastSeasons();

    List<ActionWrapper<Match>> getMatchesForSeason(long seasonId, Account account, Locale locale);

    Match getLatestMatch();

    Match getMatch(long id);

    Match createMatch(CreateMatchForm form) throws ParseException;

    Match updateMatchResult(ChangeResultForm form);

    void deleteMatch(long id) throws ObjectNotFoundException;
}
