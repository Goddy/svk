package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.model.Presence;

import java.util.Set;

/**
 * Created by u0090265 on 10/1/14.
 */
public interface DoodleService {
    public String changeMatchPresence(Account account, long matchId, boolean present);

    Presence changePresence(Account account, long accountId, long doodleId);

    boolean sendDoodleNotificationsFor(Match match, Set<Account> accounts);
}
