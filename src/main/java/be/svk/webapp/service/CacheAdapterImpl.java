/**
 * Copyright (c) 2013 KU Leuven. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of KU Leuven.
 * ("Confidential Information"). It may not be copied or reproduced in any
 * manner without the express written permission of KU Leuven.
 */
package be.svk.webapp.service;

import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.dto.MatchDTO;
import be.svk.webapp.model.Account;
import be.svk.webapp.persistence.AccountDao;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.cache2k.integration.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class CacheAdapterImpl implements CacheAdapter {
    
    private static final Logger log = LoggerFactory.getLogger(CacheAdapterImpl.class);

    private static final String ACTIVE_ACCOUNTS = "activeAccounts";

    @Autowired
    private ConcurrentDataService dataService;

    @Autowired
    private AccountDao accountDao;

    private Cache<Long, Account> accountCache =
            new Cache2kBuilder<Long, Account>() {
            }
                    .loader(new CacheLoader<Long, Account>() {
                        @Override
                        public Account load(Long l) throws Exception {
                            return accountDao.findOne(l);
                        }
                    })
                    .permitNullValues(false)
                    .expireAfterWrite(1, TimeUnit.DAYS)
                    .build();

    private Cache<String, List<Account>> activeAccountCache =
            new Cache2kBuilder<String, List<Account>>() {
            }
                    .loader(new CacheLoader<String, List<Account>>() {
                        @Override
                        public List<Account> load(String s) throws Exception {
                            return accountDao.findAllByActive(true);
                        }
                    })
                    .refreshAhead(true)
                    .expireAfterWrite(1, TimeUnit.DAYS)
                    .build();

    private Cache<Long, List<AccountStatistic>> statisticsCache =
            new Cache2kBuilder<Long, List<AccountStatistic>>() {
            }
                    .loader(new CacheLoader<Long, List<AccountStatistic>>() {
                        @Override
                        public List<AccountStatistic> load(Long l) throws Exception {
                            return dataService.getAccountStatisticsForSeason(l).get();
                        }
                    })
                    .expireAfterWrite(1, TimeUnit.DAYS)
                    .build();

    private Cache<MatchActionParameters, List<ActionWrapperDTO<MatchDTO>>> matchActionWrappersCache =
            new Cache2kBuilder<MatchActionParameters, List<ActionWrapperDTO<MatchDTO>>>() {
            }
                    .loader(new CacheLoader<MatchActionParameters, List<ActionWrapperDTO<MatchDTO>>>() {

                        @Override
                        public List<ActionWrapperDTO<MatchDTO>> load(MatchActionParameters s) throws Exception {
                            return dataService.getMatchForSeasonActionWrappers(s.seasonId, s.locale, s.account).get();
                        }
                    })
                    .expireAfterWrite(1, TimeUnit.DAYS)
                    .build();

    @Override
    public Account getAccount(Long l) {
        return accountCache.get(l);
    }

    @Override
    public List<Account> getActiveAccounts() {
        return activeAccountCache.get(ACTIVE_ACCOUNTS);
    }

    @Override
    public List<ActionWrapperDTO<MatchDTO>> getStatisticsForSeason(long seasonId, Locale locale, Account account) throws ExecutionException, InterruptedException {
        log.debug("Getting matchActionWrappers");
        return matchActionWrappersCache.get(new MatchActionParameters(seasonId, locale, account));
    }

    @Override
    public List<AccountStatistic> getStatisticsForSeason(long seasonId) throws ExecutionException, InterruptedException {
        log.debug("Getting account statistics");
        return statisticsCache.get(seasonId);
    }

    @Override
    @CacheEvict(value = {"accountStatistics"}, allEntries = true)
    public void resetStatisticsCache() {
        log.debug("resetCache - statistics");
        statisticsCache.removeAll();
    }
    
    @Override
    public void resetMatchWrappersCache() {
        log.debug("resetMatchWrappersCache - reset");
        matchActionWrappersCache.removeAll();
    }

    private class MatchActionParameters {
        private final long seasonId;
        private final Account account;
        private final Locale locale;

        public MatchActionParameters(long seasonId, Locale locale, Account account) {
            this.seasonId = seasonId;
            this.account = account;
            this.locale = locale;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MatchActionParameters that = (MatchActionParameters) o;

            if (seasonId != that.seasonId) return false;
            if (account != null ? !account.equals(that.account) : that.account != null) return false;
            return !(locale != null ? !locale.equals(that.locale) : that.locale != null);

        }

        @Override
        public int hashCode() {
            int result = (int) (seasonId ^ (seasonId >>> 32));
            result = 31 * result + (account != null ? account.hashCode() : 0);
            result = 31 * result + (locale != null ? locale.hashCode() : 0);
            return result;
        }
    }
}
