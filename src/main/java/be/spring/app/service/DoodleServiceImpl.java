package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import be.spring.app.persistence.DoodleDao;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.utils.HtmlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by u0090265 on 10/1/14.
 */
@Service
@Transactional
public class DoodleServiceImpl implements DoodleService {

    DoodleDao doodleDao;

    AccountDao accountDao;

    HtmlHelper htmlHelper;

    MatchesDao matchesDao;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DoodleServiceImpl(DoodleDao doodleDao, AccountDao accountDao, HtmlHelper htmlHelper, MatchesDao matchesDao) {
        this.doodleDao = doodleDao;
        this.accountDao = accountDao;
        this.htmlHelper = htmlHelper;
        this.matchesDao = matchesDao;
    }

    @Override
    public String changeMatchPresence(Account account, long matchId, boolean present) {
        /**
        Match match = matchesDao.findOne(matchId);
        if (match == null) throw new ObjectNotFoundException(String.format("Match with id %s not found.", matchId));
        Doodle doodle = match.getMatchDoodle();

        Presence presence = null;
        for (Presence p : doodle.getPresences()) {
            if (p.getAccount().equals(account)) {
                presence = p;
                break;
            }
        }
        changePresence(doodle, presence, present, account);
        matchesDao.save(match);

        log.info("Changed presence ({}) for match {} for account {}", present, matchId, account.getUsername() );
        return htmlHelper.getPresenceBtns(match, account);
         **/
        return null;
    }

    @Override
    public Presence changePresence(Account account, long accountId, long matchId) {

        if (account.getId().equals(accountId) || account.getRole().equals(Role.ADMIN)) {
            //Set the account that needs to be changed
            Account accountInUse = account;
            if (!account.getId().equals(accountId)) {
                accountInUse = accountDao.findOne(accountId);
                if (accountInUse == null)
                    throw new ObjectNotFoundException(String.format("Account with id %s not found.", accountId));
            }
            Match match = matchesDao.findOne(matchId);
            if (match == null) throw new ObjectNotFoundException(String.format("Match with id %s not found.", matchId));
            Doodle d = match.getMatchDoodle();

            Presence presence = null;
            for (Presence p : d.getPresences()) {
                if (p.getAccount().equals(accountInUse)) {
                    presence = p;
                    break;
                }
            }
            presence = changePresence(d, presence, accountInUse);
            doodleDao.save(d);

            log.info("Account {} changed presence ({}) for doodle {} for account {}", accountInUse.getUsername(), presence.isPresent(), matchId, accountInUse.getUsername());
            return presence;
        } else {
            log.error("Account {} is not entitled to change presence for accountId {}", account.getUsername(), accountId);
            throw new RuntimeException("Security error");
        }
    }

    private void changePresence(Doodle doodle, Presence p, boolean present, Account account) {
        if (p == null) {
            p = new Presence();
            p.setAccount(account);
            p.setPresent(present);
            doodle.getPresences().add(p);

        }
        p.setPresent(present);
    }

    private Presence changePresence(Doodle doodle, Presence p, Account account) {
        //If not created, create and set present
        if (p == null) {
            p = new Presence();
            p.setAccount(account);
            p.setPresent(true);
            doodle.getPresences().add(p);
        } else {
            //Otherwise, set the opposite
            p.setPresent(!p.isPresent());
        }
        return p;
    }
}
