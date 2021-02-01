package rscvanilla.xp.web.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.common.SystemTime;
import rscvanilla.xp.web.models.Player;
import rscvanilla.xp.web.models.PlayerExperience;
import rscvanilla.xp.web.models.PlayerExperienceDelta;
import rscvanilla.xp.web.repositories.PlayerRepository;
import rscvanilla.xp.web.services.PlayerService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final SystemTime systemTime;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, SystemTime systemTime) {
        this.playerRepository = playerRepository;
        this.systemTime = systemTime;
    }

    @Override
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name);
    }

    @Override
    public List<PlayerExperienceDelta> getPlayersExperienceDelta(int days) {
        return playerRepository.findAll()
            .stream()
            .filter(it -> it.getClosedAt() == null)
            .map(it -> getPlayerExperienceDelta(it, days))
            .collect(Collectors.toList());
    }

    private PlayerExperienceDelta getPlayerExperienceDelta(Player player, int days) {

        logger.debug("Player [{}] experience calculation:", player.getName());

        var toDate = systemTime.current().toLocalDate();
        var experiencesTo = getPlayerExperienceForDate(player, toDate);
        PlayerExperience experienceTo = null;

        if (experiencesTo.size() == 0) {
            logger.debug(" - Missing xp for today [{}].", toDate);
        } else {
            if (experiencesTo.size() > 1) {
                logger.debug(" - Multiple xp with id for today [{}].", toDate);
            }
            experienceTo = experiencesTo.get(0);
        }

        var fromDate = toDate.minusDays(days);
        var experiencesFrom = getPlayerExperienceForDate(player, fromDate);
        PlayerExperience experienceFrom = null;

        if (experiencesFrom.size() == 0) {
            logger.debug(" - Missing xp for date [{}].", fromDate);
        } else {
            if (experiencesFrom.size() > 1) {
                logger.debug(" - Multiple xp with id for date [{}].", fromDate);
            }
            experienceFrom = experiencesFrom.get(0);
        }

        if (experienceFrom == null || experienceTo == null) {
            logger.debug("Player [{}] experience calculation [FAILED].", player.getName());
            player.clearExperiences();
            return PlayerExperienceDelta.createEmpty(player.getName());
        }

        logger.debug("Player [{}] experience calculation [SUCCESS].", player.getName());
        return PlayerExperienceDelta.createComplete(player.getName(), experienceFrom, experienceTo);
    }

    private List<PlayerExperience> getPlayerExperienceForDate(Player player, LocalDate fromDate) {
        return player.getExperiences()
            .stream().filter(it -> it.getCreatedAt().toLocalDate().equals(fromDate))
            .sorted(Comparator.comparing(PlayerExperience::getCreatedAt))
            .collect(Collectors.toList());
    }
}
