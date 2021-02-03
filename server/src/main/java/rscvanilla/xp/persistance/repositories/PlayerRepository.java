package rscvanilla.xp.persistance.repositories;

import rscvanilla.xp.domain.entities.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
    List<Player> findAll();
}
