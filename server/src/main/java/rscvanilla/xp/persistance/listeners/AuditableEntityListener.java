package rscvanilla.xp.persistance.listeners;

import org.springframework.stereotype.Component;
import rscvanilla.xp.domain.services.SystemTimeService;
import rscvanilla.xp.domain.entities.AuditableEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class AuditableEntityListener {

    private final SystemTimeService systemTime;

    private AuditableEntityListener(SystemTimeService systemTime) {
        this.systemTime = systemTime;
    }

    @PrePersist
    private void onPersist(AuditableEntity entity) {
        entity.setCreatedAt(systemTime.currentTimeStamp());
        entity.setUpdatedAt(systemTime.currentTimeStamp());
    }

    @PreUpdate
    private void onUpdate(AuditableEntity entity) {
        entity.setUpdatedAt(systemTime.currentTimeStamp());
    }
}
