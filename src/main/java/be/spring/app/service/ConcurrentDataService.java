package be.spring.app.service;

import be.spring.app.data.AccountStatistic;
import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
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

    ListenableFuture<List<ActionWrapper<Match>>> getMatchForSeasonActionWrappers(long seasonId, Account account, Locale locale);

    ListenableFuture<List<ActionWrapper<Team>>> getTeamsActionWrappers(Account account, Locale locale);
}
