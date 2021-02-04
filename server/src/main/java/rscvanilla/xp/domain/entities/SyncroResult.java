package rscvanilla.xp.domain.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rscvanilla.xp.domain.models.SyncroResultStatus;
import rscvanilla.xp.persistance.listeners.AuditableEntityListener;

import javax.persistence.*;

@Entity
@Builder
@EntityListeners(AuditableEntityListener.class)
public class SyncroResult extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column
    @Getter @Setter
    private SyncroResultStatus status;

    @Column
    @Getter @Setter
    private int tryCount;

}
