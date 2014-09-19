package be.spring.app.tasks;

import be.spring.app.interfaces.PwdRecoveryService;
import be.spring.app.interfaces.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by u0090265 on 9/19/14.
 */
@Component
public class RecoveryCodeCleanupTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(RecoveryCodeCleanupTask.class);
    @Autowired
    private PwdRecoveryService pwdRecoveryService;

    @Override
    public void execute() {
        log.info("Execute RecoveryCodeCleanupTask - start");
        pwdRecoveryService.deleteExpiredCodes();
        log.info("Execute RecoveryCodeCleanupTask - end");
    }
}
