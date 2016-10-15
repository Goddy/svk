package be.spring.app.service;

import be.spring.app.data.AccountStatistic;
import be.spring.app.dto.ActionWrapperDTO;
import be.spring.app.dto.MatchDTO;
import be.spring.app.model.Account;
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
