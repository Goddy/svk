package be.spring.app.tasks;

import be.spring.app.service.AccountService;
import be.spring.app.service.DoodleService;
import be.spring.app.service.MatchesService;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by u0090265 on 8/24/15.
 */
public class DoodleReminderTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(DoodleReminderTask.class);

    @Autowired
    private DoodleService doodleService;

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private AccountService accountService;


    @Override
    public void execute() {
        log.info("Execute DoodleReminderTask - start");
        doodleService.sendDoodleNotificationsFor(matchesService.getLatestMatch(), Sets.newHashSet(accountService.getAccountsByActivationStatus(true)));
        log.info("Execute DoodleReminderTask - end");
    }
}
