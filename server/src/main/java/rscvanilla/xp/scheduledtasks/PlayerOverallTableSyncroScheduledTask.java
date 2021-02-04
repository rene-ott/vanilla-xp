package rscvanilla.xp.scheduledtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.domain.models.SyncroResultStatus;
import rscvanilla.xp.domain.services.PlayerOverallTableSyncroService;
import rscvanilla.xp.domain.services.SyncroResultService;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.infrastructure.time.SystemTimeContext;

@Component
public class PlayerOverallTableSyncroScheduledTask {

    private Logger logger = LoggerFactory.getLogger(PlayerOverallTableSyncroScheduledTask.class);

    private final PlayerOverallTableSyncroService playerOverallTableSyncroService;
    private final SyncroResultService syncroResultService;
    private final SystemTime systemTime;
    private final SystemTimeContext systemTimeContext;

    @Autowired
    public PlayerOverallTableSyncroScheduledTask(PlayerOverallTableSyncroService playerOverallTableSyncroService,
                                                 SyncroResultService syncroResultService,
                                                 SystemTime systemTime,
                                                 SystemTimeContext systemTimeContext) {
        this.playerOverallTableSyncroService = playerOverallTableSyncroService;
        this.syncroResultService = syncroResultService;
        this.systemTime = systemTime;
        this.systemTimeContext = systemTimeContext;
    }

    @Scheduled(fixedDelay = 18000, initialDelay = 5000)
    public void run() {
        logger.info("Checking necessity of RSC Vanilla overall table synchronization.");

        systemTimeContext.setTime(SystemTime.now());
        var results = syncroResultService.findByTodayDate();

        SyncroResult foundResult = null;

        if (!results.isEmpty()) {
            foundResult = results.get(0);

            var time = SystemTime.toCurrentDateTime(foundResult.getCreatedAt());
            if (foundResult.getStatus() == SyncroResultStatus.OK) {
                logger.info("Table is already successfully synchronized at [{}] not necessary to synchronize. ", time);

                systemTimeContext.clearTime();
                return;

            } else {
                logger.info("Table synchronization has failed [{}] times in a row. Latest failure was at [{}]. Starting synchronization again...", foundResult.getTryCount(), time);
            }
        } else {
            logger.info("Table has not been synchronized today [{}]. Starting synchronization...", systemTime.currentDate());
        }

        try {
            playerOverallTableSyncroService.synchronizeToDatabase();
        } catch (Exception e) {
            logger.error("Synchronization [FAILED].", e);

            if (foundResult == null) {
                foundResult = SyncroResult.builder().status(SyncroResultStatus.NOK).tryCount(1).build();
            } else {
                foundResult.setStatus(SyncroResultStatus.NOK);
                foundResult.setTryCount(foundResult.getTryCount() + 1);
            }

            syncroResultService.insertOrUpdate(foundResult);
            systemTimeContext.clearTime();
        }
        logger.info("Synchronization [SUCCEEDED].");
        foundResult = SyncroResult.builder().status(SyncroResultStatus.OK).tryCount(1).build();
        syncroResultService.insertOrUpdate(foundResult);
        systemTimeContext.clearTime();
    }
}
