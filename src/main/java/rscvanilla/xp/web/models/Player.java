package rscvanilla.xp.web.models;

import lombok.*;
import org.hibernate.annotations.Where;
import rscvanilla.xp.web.listeners.AuditableEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditableEntityListener.class)
@RequiredArgsConstructor
public class Player extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column(unique = true)
    @Getter @Setter(AccessLevel.PROTECTED)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "player", cascade = CascadeType.ALL)
    @Getter @Setter(AccessLevel.PROTECTED)
    private List<PlayerExperience> experiences = new ArrayList<>();

    public boolean hasSameName(Player player) {
        return name.equals(player.getName());
    }

    @Builder
    public Player(String name) {
        this.name = name;
    }

    public void addExperience(PlayerExperience experience) {
        experiences.add(experience);
        experience.setPlayer(this);
    }
}
