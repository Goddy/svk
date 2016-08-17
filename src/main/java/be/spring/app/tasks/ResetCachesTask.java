package be.spring.app.tasks;

import be.spring.app.service.CacheAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by u0090265 on 4/23/15.
 */
public class ResetCachesTask implements Task {
    private static final Logger log = LoggerFactory.getLogger(ResetCachesTask.class);
    @Autowired
    private CacheAdapter cacheAdapter;

    @Scheduled(fixedDelay = 3600000, zone = "Europe/Brussels")
    @Override
    public void execute() {
        cacheAdapter.resetMatchCache();
        log.info("Cleaned up the matches.");
    }
}
