package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.dto.ActionWrapperDTO;
import be.spring.app.dto.MatchDTO;
import be.spring.app.form.ChangeResultForm;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.model.Season;
import com.google.common.base.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface MatchesService {
    Map<Integer, List<Match>> getMatchesForLastSeasons();

    List<Match> getMatchesListForSeason(Season season);

    Page<Match> getUpcomingMatchesPages(int start);

    List<ActionWrapperDTO<MatchDTO>> getMatchesWrappersForSeason(long seasonId, Locale locale, Account account);

    List<Match> getMatchesForSeason(long seasonId);

    List<Match> getMatchesForSeason(String description);

    Match getLatestMatch();

    Match getLatestMatchWithPoll();

    List<Match> getMatchesWithPolls(int page, int pageSize, Optional<Sort> sort, Optional<String> searchTerm);

    Match getMatch(long id);

    Match createMatch(CreateMatchForm form) throws ParseException;

    Match updateMatch(ChangeResultForm form);

    void deleteMatch(long id) throws ObjectNotFoundException;
}
