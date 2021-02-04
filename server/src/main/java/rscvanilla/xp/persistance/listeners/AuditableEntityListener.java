package rscvanilla.xp.persistance.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rscvanilla.xp.domain.entities.AuditableEntity;
import rscvanilla.xp.infrastructure.time.SystemTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class AuditableEntityListener {

    private final SystemTime systemTime;

    @Autowired
    private AuditableEntityListener(SystemTime systemTime) {
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
