package rscvanilla.xp.domain.services;

import rscvanilla.xp.domain.models.PlayerOverallStateChange;

import java.util.List;

public interface PlayerService {
    List<PlayerOverallStateChange> getPlayerOverallStateChanges(int daysBeforeToday);
}
