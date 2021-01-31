package rscvanilla.xp.web.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Getter @Setter(AccessLevel.PROTECTED)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    @Getter @Setter(AccessLevel.PROTECTED) @Singular("experiences")
    private List<PlayerExperienceChange> experience;
}
