package rscvanilla.xp.web.models;

import lombok.*;
import rscvanilla.xp.web.listeners.AuditableEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditableEntityListener.class)
@RequiredArgsConstructor
public class PlayerExperience extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Getter @Setter(AccessLevel.PROTECTED)
    private int rank;

    @Getter @Setter(AccessLevel.PROTECTED)
    private int xp;

    @Getter @Setter(AccessLevel.PROTECTED)
    private int level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    @Getter @Setter(AccessLevel.PACKAGE)
    private Player player;

    @Builder
    public PlayerExperience(int rank, int xp, int level) {
        this.rank = rank;
        this.xp = xp;
        this.level = level;
    }
}
