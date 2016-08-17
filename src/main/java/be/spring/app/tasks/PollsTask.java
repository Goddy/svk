package be.spring.app.tasks;

import be.spring.app.data.PollStatusEnum;
import be.spring.app.model.Poll;
import be.spring.app.persistence.PollDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by u0090265 on 16/07/16.
 */
public class PollsTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(CleanupTask.class);

    @Autowired
    PollDao pollDao;

    @Scheduled(fixedDelay = 900000, zone = "Europe/Brussels")
    @Override
    public void execute() {
        log.info("Execute pollsTask - start");
        for (Poll p : pollDao.findByStatus(PollStatusEnum.OPEN)) {
            if (p.getEndDate().isBeforeNow()) {
                p.setStatus(PollStatusEnum.CLOSED);
                pollDao.save(p);
                log.info(String.format("Closed poll id %s with question %s (enddate: %s", p.getId(), p.getQuestion(), p.getEndDate()));
            }
        }
        log.info("Execute pollsTask - end");
    }
}
