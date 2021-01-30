package rscvanilla.xp.repositories;

import rscvanilla.xp.models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
