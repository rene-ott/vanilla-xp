package rscvanilla.xp.domain.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rscvanilla.xp.domain.models.SyncroResultStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
public class SyncroResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column
    @Getter @Setter(AccessLevel.PROTECTED)
    private SyncroResultStatus status;

    @Column
    @Getter @Setter(AccessLevel.PROTECTED)
    private int tryCount;

    @Column
    @Getter @Setter(AccessLevel.PROTECTED)
    private Instant createdAt;
}
