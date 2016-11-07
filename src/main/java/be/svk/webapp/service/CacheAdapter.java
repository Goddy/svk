package be.svk.webapp.service;

import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.dto.MatchDTO;
import be.svk.webapp.model.Account;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by u0090265 on 11/3/14.
 */
public interface CacheAdapter {
    Account getAccount(Long l);

    List<Account> getActiveAccounts();

    @Cacheable("matchActionWrappers")
    List<ActionWrapperDTO<MatchDTO>> getStatisticsForSeason(long seasonId, Locale locale, Account account) throws
            ExecutionException, InterruptedException;

    @Cacheable("accountStatistics")
    List<AccountStatistic> getStatisticsForSeason(long seasonId) throws ExecutionException, InterruptedException;

    @CacheEvict(value = {"accountStatistics"}, allEntries = true)
    void resetStatisticsCache();

    @CacheEvict(value={"matchActionWrappers"}, allEntries = true)
    void resetMatchWrappersCache();
}
