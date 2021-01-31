package rscvanilla.xp.web.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class PlayerExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private int attack;

    @Getter @Setter
    private int defense;

    @Getter @Setter
    private int strength;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    @Getter @Setter @NonNull
    private Player player;
}
