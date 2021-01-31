package rscvanilla.xp.web.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")

    @Getter @Setter
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    @Getter @Setter
    protected LocalDateTime updatedAt;

    @Column(name = "closed_at", columnDefinition = "TIMESTAMP")
    @Getter @Setter(AccessLevel.PROTECTED)
    protected LocalDateTime closedAt;

    public void close(LocalDateTime localDateTime) {
        closedAt = localDateTime;
    }
}
