package rscvanilla.xp.web.repositories;

import rscvanilla.xp.web.models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
    List<Player> findAll();
}
