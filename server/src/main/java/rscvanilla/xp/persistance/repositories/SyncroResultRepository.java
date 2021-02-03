package rscvanilla.xp.persistance.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rscvanilla.xp.domain.entities.SyncroResult;

import java.time.Instant;
import java.util.List;

public interface SyncroResultRepository extends CrudRepository<SyncroResult, Long> {
    @Query("SELECT r FROM SyncroResult r WHERE r.createdAt BETWEEN :createdAtStart AND :createdAtEnd ORDER BY r.createdAt")
    List<SyncroResult> findAllBetween(Instant createdAtStart, Instant createdAtEnd);
}
