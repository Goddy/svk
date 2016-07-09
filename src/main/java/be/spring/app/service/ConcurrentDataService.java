package be.spring.app.service;

import be.spring.app.data.AccountStatistic;
import be.spring.app.model.Account;
import be.spring.app.dto.ActionWrapperDTO;
import be.spring.app.model.Match;
import be.spring.app.model.Team;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 9/12/14.
 */
public interface ConcurrentDataService {
    ListenableFuture<List<AccountStatistic>> getAccountStatisticsForSeason(long seasonId);

    ListenableFuture<List<ActionWrapperDTO<Match>>> getMatchForSeasonActionWrappers(long seasonId, Account account, Locale locale);

    ListenableFuture<List<ActionWrapperDTO<Team>>> getTeamsActionWrappers(Account account, Locale locale);
}
