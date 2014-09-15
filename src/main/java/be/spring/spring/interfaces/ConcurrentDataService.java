package be.spring.spring.interfaces;

import be.spring.spring.model.Account;
import be.spring.spring.model.ActionWrapper;
import be.spring.spring.model.Match;
import be.spring.spring.model.Team;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 9/12/14.
 */
public interface ConcurrentDataService {
    ListenableFuture<List<ActionWrapper<Match>>> getMatchForSeasonActionWrappers(String seasonId, Account account, Locale locale);

    ListenableFuture<List<ActionWrapper<Team>>> getTeamsActionWrappers(Account account, Locale locale);
}
