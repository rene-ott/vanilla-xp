package rscvanilla.xp.domain.services;

import rscvanilla.xp.domain.entities.SyncroResult;

import java.util.List;

public interface SyncroResultService {
    List<SyncroResult> findByTodayDate();
    void insertOrUpdate(SyncroResult syncroResult);
}
