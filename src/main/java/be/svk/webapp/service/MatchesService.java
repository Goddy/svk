package be.svk.webapp.service;

import be.svk.webapp.controller.exceptions.ObjectNotFoundException;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.dto.MatchDTO;
import be.svk.webapp.form.ChangeResultForm;
import be.svk.webapp.form.CreateMatchForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Season;
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

    Page<Match> getUpcomingMatchesPages(int page, int pageSize, Optional<Sort> sort);

    List<ActionWrapperDTO<MatchDTO>> getMatchesWrappersForSeason(long seasonId, Locale locale, Account account);

    List<Match> getMatchesForSeason(long seasonId);

    List<Match> getMatchesForSeason(String description);

    Match getLatestMatch();

    Match getLatestMatchWithPoll();

    Page<Match> getMatchesWithPolls(int page, int pageSize, Optional<Sort> sort, Optional<String> searchTerm);

    Match getMatch(long id);

    Match createMatch(CreateMatchForm form) throws ParseException;

    Match updateMatch(ChangeResultForm form);

    void deleteMatch(long id) throws ObjectNotFoundException;
}
