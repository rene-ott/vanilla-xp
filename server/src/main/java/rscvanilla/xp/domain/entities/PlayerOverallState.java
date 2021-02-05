package rscvanilla.xp.domain.entities;

import lombok.*;
import rscvanilla.xp.persistance.listeners.AuditableEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditableEntityListener.class)
@RequiredArgsConstructor
public class PlayerOverallState extends CloseableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column(nullable = false)
    @Getter @Setter(AccessLevel.PROTECTED)
    private int rank;

    @Column(nullable = false)
    @Getter @Setter(AccessLevel.PROTECTED)
    private int xp;

    @Column(nullable = false)
    @Getter @Setter(AccessLevel.PROTECTED)
    private int level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    @Getter @Setter(AccessLevel.PACKAGE)
    private Player player;

    @Builder
    public PlayerOverallState(int rank, int xp, int level) {
        this.rank = rank;
        this.xp = xp;
        this.level = level;
    }
}
