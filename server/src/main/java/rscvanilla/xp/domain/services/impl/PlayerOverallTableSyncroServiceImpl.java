package rscvanilla.xp.domain.services.impl;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.domain.entities.CloseableEntity;
import rscvanilla.xp.domain.services.PlayerOverallTableSyncroService;
import rscvanilla.xp.domain.models.PlayerOverallTableRow;
import rscvanilla.xp.domain.services.PlayerOverallTableCrawlerService;
import rscvanilla.xp.domain.entities.Player;
import rscvanilla.xp.domain.entities.PlayerOverallState;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.persistance.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerOverallTableSyncroServiceImpl implements PlayerOverallTableSyncroService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerOverallTableSyncroService.class);

    private final PlayerOverallTableCrawlerService playerOverallTableCrawlerService;
    private final PlayerRepository playerRepository;
    private final SystemTime systemTime;

    @Autowired
    public PlayerOverallTableSyncroServiceImpl(PlayerOverallTableCrawlerService playerOverallTableCrawlerService,
                                               PlayerRepository playerRepository,
                                               SystemTime systemTime) {
        this.playerOverallTableCrawlerService = playerOverallTableCrawlerService;
        this.playerRepository = playerRepository;
        this.systemTime = systemTime;
    }

    public void synchronizeToDatabase() {
        var playersFromWeb = playerOverallTableCrawlerService.getAllPlayerNamesFromWeb()
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());

        var playersFromDatabase = ImmutableList.copyOf(playerRepository.findAll());

        var transactedPlayers = new ArrayList<Player>();

        for (var webPlayer : playersFromWeb) {
            playersFromDatabase.stream()
                .filter(dbPlayer -> dbPlayer.hasSameName(webPlayer))
                .findFirst()
                .ifPresentOrElse(
                    dbPlayer -> updatePlayer(webPlayer, dbPlayer, transactedPlayers),
                    () -> createPlayer(webPlayer, transactedPlayers)
                );
        }

        for (var dbPlayer : playersFromDatabase) {
            playersFromWeb.stream()
                    .filter(webPlayer -> webPlayer.hasSameName(dbPlayer))
                    .findFirst()
                    .ifPresentOrElse(player -> {}, () -> closePlayer(dbPlayer, transactedPlayers));
        }

        playerRepository.saveAll(transactedPlayers);
    }

    private Player convertToModel(PlayerOverallTableRow row) {
        var experience = PlayerOverallState.builder()
            .level(row.getLevel())
            .rank(row.getRank())
            .xp(row.getXp())
            .build();

        var player = Player.builder()
            .name(row.getPlayer())
            .build();

        player.addState(experience);

        return player;
    }
    
    private void closePlayer(Player existingPlayer, List<Player> updatablePlayers) {
        logger.debug("Closed player [{}].", existingPlayer.getName());

        existingPlayer.close(systemTime.currentTimeStamp());
        existingPlayer.getExperiences().forEach(it -> it.close(systemTime.currentTimeStamp()));

        updatablePlayers.add(existingPlayer);
    }

    private void createPlayer(Player webPlayer, List<Player> players) {
        logger.debug("New player [{}].", webPlayer.getName());
        players.add(webPlayer);
    }

    private void updatePlayer(Player webPlayer, Player dbPlayer, List<Player> players) {

        if (dbPlayer.isOpened()) {
            logger.debug("Existing player [{}].", dbPlayer.getName());
        } else {
            logger.debug("Existing [CLOSED] player [{}].", dbPlayer.getName());
            dbPlayer.open();
            dbPlayer.getExperiences().forEach(CloseableEntity::open);
        }

        var experience = webPlayer.getExperiences().stream().findFirst().orElseThrow();
        dbPlayer.addState(experience);

        players.add(dbPlayer);
    }
}
