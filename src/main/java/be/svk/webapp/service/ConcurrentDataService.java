package be.svk.webapp.service;

import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.dto.MatchDTO;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Team;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 9/12/14.
 */
public interface ConcurrentDataService {
    ListenableFuture<List<AccountStatistic>> getAccountStatisticsForSeason(long seasonId);

    ListenableFuture<List<ActionWrapperDTO<MatchDTO>>> getMatchForSeasonActionWrappers(long seasonId, Locale locale, Account account);

    ListenableFuture<List<ActionWrapperDTO<Team>>> getTeamsActionWrappers(Account account, Locale locale);
}
