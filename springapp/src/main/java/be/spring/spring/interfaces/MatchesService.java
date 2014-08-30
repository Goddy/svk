package be.spring.spring.interfaces;

import be.spring.spring.controller.exceptions.ObjectNotFoundException;
import be.spring.spring.form.ChangeResultForm;
import be.spring.spring.form.CreateMatchForm;
import be.spring.spring.model.Account;
import be.spring.spring.model.ActionWrapper;
import be.spring.spring.model.Match;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesService {
    Map<Integer, List<Match>> getMatchesForLastSeasons();

    List<ActionWrapper<Match>> getMatchesForSeason(String seasonId, Account account, Locale locale);

    Match getMatch(String id);

    Match createMatch(CreateMatchForm form) throws ParseException;

    Match updateMatchResult(ChangeResultForm form);

    void deleteMatch(String id) throws ObjectNotFoundException;
}
