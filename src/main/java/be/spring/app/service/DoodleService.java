package be.spring.app.service;

import be.spring.app.model.Account;

/**
 * Created by u0090265 on 10/1/14.
 */
public interface DoodleService {
    public String changeMatchPresence(Account account, long matchId, boolean present);
}
