package rscvanilla.xp.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public class AuditableEntity {

    @Column(name = "created_at")
    @Getter @Setter
    protected Instant createdAt;

    @Column(name = "updated_at")
    @Getter @Setter
    protected Instant updatedAt;
}
