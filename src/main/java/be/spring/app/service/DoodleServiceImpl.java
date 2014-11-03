package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.Doodle;
import be.spring.app.model.Presence;
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
    @Autowired
    DoodleDao doodleDao;

    @Autowired
    HtmlHelper htmlHelper;

    @Autowired
    MatchesDao matchesDao;

    Logger log = LoggerFactory.getLogger(this.getClass());

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

    private void changePresence(Doodle doodle, Presence p, boolean present, Account account) {
        if (p == null) {
            p = new Presence();
            p.setAccount(account);
            p.setPresent(present);
            doodle.getPresences().add(p);

        }
        p.setPresent(present);
    }
}
