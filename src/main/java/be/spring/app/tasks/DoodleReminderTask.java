package be.spring.app.tasks;

import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.model.Presence;
import be.spring.app.service.AccountService;
import be.spring.app.service.MailService;
import be.spring.app.service.MatchesService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Created by u0090265 on 8/24/15.
 */
public class DoodleReminderTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(CleanupTask.class);
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private AccountService accountService;

    @Override
    public void execute() {
        log.info("Execute DoodleReminderTask - start");

        Match match = matchesService.getLatestMatch();

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        String matchDate = dtf.print(match.getDate());

        //Make sure the next match is this week
        if (match.getDate().weekOfWeekyear().equals(DateTime.now().weekOfWeekyear())) {
            //Loop all active accounts
            for (Account account : accountService.getAccountsByActivationStatus(true)) {
                //Make sure they enabled the notifications
                if (account.getAccountSettings().isSendDoodleNotifications()) {
                    //If they didn't fill in the doodle, send mail
                    if (match.getMatchDoodle().isPresent(account).equals(Presence.PresenceType.NOT_FILLED_IN)) {
                        String subject = messageSource.getMessage("email.doodle.subject", null, Locale.ENGLISH);
                        String body = messageSource.getMessage("email.doodle.body", new String[]{account.getFirstName(), match.getDescription(), matchDate}, Locale.ENGLISH);
                        mailService.sendMail(account.getUsername(), subject, body);
                    } else {
                        log.info("Account {} has filled in doodle", account.getUsername());
                    }
                } else {
                    log.info("Account {} has disabled doodle notifications, not sending email", account.getUsername());
                }
            }
        } else {
            log.info("Match id {} not starting this week, aborting", match.getId());
        }

        log.info("Execute DoodleReminderTask - end");
    }
}
