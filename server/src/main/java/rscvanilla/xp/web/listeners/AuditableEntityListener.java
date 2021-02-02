package rscvanilla.xp.web.listeners;

import org.springframework.stereotype.Component;
import rscvanilla.xp.common.SystemTime;
import rscvanilla.xp.web.models.AuditableEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class AuditableEntityListener {

    private final SystemTime systemTime;

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
