package rscvanilla.xp.web.services;

import rscvanilla.xp.web.models.Player;
import rscvanilla.xp.web.models.PlayerExperienceDelta;

import java.util.List;

public interface PlayerService {
    List<PlayerExperienceDelta> getPlayersExperienceDelta(int days);
    Player getPlayerByName(String name);
}
