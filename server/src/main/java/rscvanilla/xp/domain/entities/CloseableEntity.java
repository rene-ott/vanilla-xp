package rscvanilla.xp.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public class CloseableEntity extends AuditableEntity {
    @Column(name = "closed_at")
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected Instant closedAt;

    public void close(Instant instant) {
        closedAt = instant;
    }

    public void open() {
        closedAt = null;
    }

    public boolean isOpened() {
        return closedAt == null;
    }
}
