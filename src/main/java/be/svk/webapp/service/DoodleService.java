package be.svk.webapp.service;

import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Presence;

import java.util.Set;

/**
 * Created by u0090265 on 10/1/14.
 */
public interface DoodleService {
    String changeMatchPresence(Account account, long matchId, boolean present);

    Presence changePresence(Account account, long accountId, long doodleId);

    boolean sendDoodleNotificationsFor(Match match, Set<Account> accounts);
}
