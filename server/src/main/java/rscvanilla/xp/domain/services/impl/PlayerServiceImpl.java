package rscvanilla.xp.domain.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.domain.services.SystemTimeService;
import rscvanilla.xp.domain.entities.Player;
import rscvanilla.xp.domain.entities.PlayerOverallState;
import rscvanilla.xp.domain.models.PlayerOverallStateChange;
import rscvanilla.xp.persistance.repositories.PlayerRepository;
import rscvanilla.xp.domain.services.PlayerService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final SystemTimeService systemTime;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, SystemTimeService systemTime) {
        this.playerRepository = playerRepository;
        this.systemTime = systemTime;
    }

    @Override
    public List<PlayerOverallStateChange> getPlayerOverallStateChanges(int days) {
        return playerRepository.findAll()
            .stream()
            .filter(it -> it.getClosedAt() == null)
            .map(it -> getPlayerOverallStateChange(it, days))
            .collect(Collectors.toList());
    }

    private PlayerOverallStateChange getPlayerOverallStateChange(Player player, int daysBeforeToday) {

        logger.debug("Player [{}] experience calculation:", player.getName());

        var currentDate = systemTime.currentDate();
        var currentOverallStates = findPlayerOverallStateByDate(player, currentDate);
        PlayerOverallState selectedCurrentOverallState = null;

        if (currentOverallStates.size() == 0) {
            logger.debug(" - Missing xp for today [{}].", currentDate);
        } else {
            if (currentOverallStates.size() > 1) {
                logger.debug(" - Multiple xp with id for today [{}].", currentDate);
            }
            selectedCurrentOverallState = currentOverallStates.get(0);
        }

        var previousDate = currentDate.minusDays(daysBeforeToday);
        var previousOverallStates = findPlayerOverallStateByDate(player, previousDate);
        PlayerOverallState selectedPreviousOverallState = null;

        if (previousOverallStates.size() == 0) {
            logger.debug(" - Missing xp for date [{}].", previousDate);
        } else {
            if (previousOverallStates.size() > 1) {
                logger.debug(" - Multiple xp with id for date [{}].", previousDate);
            }
            selectedPreviousOverallState = previousOverallStates.get(0);
        }

        if (selectedPreviousOverallState == null || selectedCurrentOverallState == null) {
            logger.debug("Player [{}] experience calculation [FAILED].", player.getName());
            player.clearOverallStates();
            return PlayerOverallStateChange.createEmpty(player.getName());
        }

        logger.debug("Player [{}] experience calculation [SUCCESS].", player.getName());
        return PlayerOverallStateChange.createComplete(player.getName(), selectedPreviousOverallState, selectedCurrentOverallState);
    }

    private List<PlayerOverallState> findPlayerOverallStateByDate(Player player, LocalDate fromDate) {
        return player.getExperiences()
            .stream().filter(it -> SystemTimeService.toCurrentDate(it.getCreatedAt()).equals(fromDate))
            .sorted(Comparator.comparing(PlayerOverallState::getCreatedAt))
            .collect(Collectors.toList());
    }
}
