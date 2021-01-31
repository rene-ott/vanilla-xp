package rscvanilla.xp.web.models;

import lombok.*;
import org.hibernate.annotations.Where;
import rscvanilla.xp.web.listeners.AuditableEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditableEntityListener.class)
@Where(clause = "closed_at IS NULL")
@AllArgsConstructor
@Builder
public class Player extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Getter @Setter
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    @Getter @Setter @Singular("experiences")
    private List<PlayerExperience> experience;

    public boolean hasSameName(Player player) {
        return name.equals(player.getName());
    }
}
