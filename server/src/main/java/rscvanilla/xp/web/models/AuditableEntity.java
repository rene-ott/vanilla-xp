package rscvanilla.xp.web.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@MappedSuperclass
public class AuditableEntity {

    @Column(name = "created_at")

    @Getter @Setter
    protected Instant createdAt;

    @Column(name = "updated_at")
    @Getter @Setter
    protected Instant updatedAt;

    @Column(name = "closed_at")
    @Getter @Setter(AccessLevel.PROTECTED)
    protected Instant closedAt;

    public void close(Instant instant) {
        closedAt = instant;
    }

    public LocalDate getCreatedAtDate() {
        return LocalDate.ofInstant(createdAt, ZoneOffset.UTC);
    }
}
