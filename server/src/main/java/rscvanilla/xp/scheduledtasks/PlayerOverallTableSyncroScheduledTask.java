package rscvanilla.xp.scheduledtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rscvanilla.xp.domain.services.PlayerOverallTableSyncroService;

@Component
public class PlayerOverallTableSyncroScheduledTask {

    private Logger logger = LoggerFactory.getLogger(PlayerOverallTableSyncroScheduledTask.class);

    @Autowired
    private PlayerOverallTableSyncroService playerOverallTableSyncroService;

    @Scheduled(fixedDelay = 18000, initialDelay = 10000)
    public void run() {

        playerOverallTableSyncroService.synchronizeToDatabase();
    }
}
