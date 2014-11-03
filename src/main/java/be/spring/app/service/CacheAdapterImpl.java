/**
 * Copyright (c) 2013 KU Leuven. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of KU Leuven.
 * ("Confidential Information"). It may not be copied or reproduced in any
 * manner without the express written permission of KU Leuven.
 */
package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Match;
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
    public List<ActionWrapper<Match>> getMatchActionWrappers(long seasonId, Account account, Locale locale) throws ExecutionException, InterruptedException {
        log.debug("Getting matchActionWrappers");
        return dataService.getMatchForSeasonActionWrappers(seasonId, account, locale).get();
    }
    
    @Override
    @CacheEvict(value={"matchActionWrappers"}, allEntries = true)
    public void resetMatchCache() {
        log.debug("resetCache");
    }
}
