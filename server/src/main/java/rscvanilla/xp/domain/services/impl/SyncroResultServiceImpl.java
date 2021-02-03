package rscvanilla.xp.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.domain.services.SyncroResultService;
import rscvanilla.xp.domain.services.SystemTimeService;
import rscvanilla.xp.persistance.repositories.SyncroResultRepository;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

public class SyncroResultServiceImpl implements SyncroResultService {

    private final SyncroResultRepository syncroResultRepository;
    private final SystemTimeService systemTimeService;

    @Autowired
    public SyncroResultServiceImpl(SyncroResultRepository syncroResultRepository,
                                   SystemTimeService systemTimeService) {
        this.syncroResultRepository = syncroResultRepository;
        this.systemTimeService = systemTimeService;
    }

    public List<SyncroResult> findByTodayDate() {
        var currentDate = systemTimeService.currentDate();
        var startOfTheDay = currentDate.atStartOfDay(ZoneOffset.UTC);
        var endOfTheDay = LocalTime.MAX.atDate(currentDate);

        return syncroResultRepository.findAllBetween(startOfTheDay.toInstant(), endOfTheDay.toInstant(ZoneOffset.UTC));
    }
}
