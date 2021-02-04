package rscvanilla.xp.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.domain.services.SyncroResultService;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.persistance.repositories.SyncroResultRepository;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class SyncroResultServiceImpl implements SyncroResultService {

    private final SyncroResultRepository syncroResultRepository;
    private final SystemTime systemTime;

    @Autowired
    public SyncroResultServiceImpl(SyncroResultRepository syncroResultRepository,
                                   SystemTime systemTime) {
        this.syncroResultRepository = syncroResultRepository;
        this.systemTime = systemTime;
    }

    @Override
    public List<SyncroResult> findByTodayDate() {
        var startOfTheDay = systemTime.currentDateStartOfTheDay().toInstant(ZoneOffset.UTC);
        var endOfTheDay = systemTime.currentDateEndOfTheDay().toInstant(ZoneOffset.UTC);

        return syncroResultRepository.findAllBetween(startOfTheDay, endOfTheDay);
    }

    @Override
    public void insertOrUpdate(SyncroResult syncroResult) {
        syncroResultRepository.save(syncroResult);
    }

    @Override
    public SyncroResult getLatestForToday() {
        var results = findByTodayDate();
        return results.isEmpty() ? SyncroResult.createMissingResult() : results.get(0);
    }
}
