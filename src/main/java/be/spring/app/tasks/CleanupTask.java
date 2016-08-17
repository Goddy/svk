package be.spring.app.tasks;

import be.spring.app.persistence.CleanUpDao;
import be.spring.app.service.PwdRecoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by u0090265 on 9/19/14.
 */

public class CleanupTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(CleanupTask.class);
    @Autowired
    CleanUpDao cleanUpDao;
    @Autowired
    private PwdRecoveryService pwdRecoveryService;

    @Scheduled(cron = "0 */30 * * * *", zone = "Europe/Brussels")
    @Override
    public void execute() {
        log.info("Execute CleanupTask - start");
        pwdRecoveryService.deleteExpiredCodes();
        //cleanUpDao.cleanGoals();
        log.info("Execute CleanupTask - end");
    }
}
