/**
 * Copyright (c) 2013 KU Leuven. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of KU Leuven.
 * ("Confidential Information"). It may not be copied or reproduced in any
 * manner without the express written permission of KU Leuven.
 */
package be.spring.app.service;

import be.spring.app.data.AccountStatistic;
import be.spring.app.dto.ActionWrapperDTO;
import be.spring.app.dto.MatchDTO;
import be.spring.app.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Component
public class CacheAdapterImpl implements CacheAdapter {
    
    private static final Logger log = LoggerFactory.getLogger(CacheAdapterImpl.class);
    
    @Autowired
    private ConcurrentDataService dataService;

    @Override
    @Cacheable("matchActionWrappers")
    public List<ActionWrapperDTO<MatchDTO>> getMatchActionWrappers(long seasonId, Locale locale, Account account) throws ExecutionException, InterruptedException {
        log.debug("Getting matchActionWrappers");
        return dataService.getMatchForSeasonActionWrappers(seasonId, locale, account).get();
    }

    @Override
    @Cacheable("accountStatistics")
    public List<AccountStatistic> getMatchActionWrappers(long seasonId) throws ExecutionException, InterruptedException {
        log.debug("Getting account statistics");
        return dataService.getAccountStatisticsForSeason(seasonId).get();
    }

    @Override
    @CacheEvict(value = {"accountStatistics"}, allEntries = true)
    public void resetStatisticsCache() {
        log.debug("resetCache - statistics");
    }
    
    @Override
    @CacheEvict(value={"matchActionWrappers"}, allEntries = true)
    public void resetMatchCache() {
        log.debug("resetCache - matchWrappers");
    }
}
