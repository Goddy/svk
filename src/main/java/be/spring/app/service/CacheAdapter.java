package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Match;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by u0090265 on 11/3/14.
 */
public interface CacheAdapter {
    @Cacheable("matchActionWrappers")
    List<ActionWrapper<Match>> getMatchActionWrappers(long seasonId, Account account, Locale locale) throws ExecutionException, InterruptedException;

    @CacheEvict(value={"matchActionWrappers"}, allEntries = true)
    void resetMatchCache();
}
