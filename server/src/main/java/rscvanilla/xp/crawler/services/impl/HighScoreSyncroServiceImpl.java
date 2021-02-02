package rscvanilla.xp.crawler.services.impl;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.common.SystemTime;
import rscvanilla.xp.crawler.models.PlayerOverallRankTableRow;
import rscvanilla.xp.crawler.services.HighScoreCrawlerService;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import rscvanilla.xp.web.models.Player;
import rscvanilla.xp.web.models.PlayerExperience;
import rscvanilla.xp.web.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HighScoreSyncroServiceImpl implements HighScoreSyncroService {

    private static final Logger logger = LoggerFactory.getLogger(HighScoreSyncroService.class);

    @Autowired
    private final HighScoreCrawlerService highScoreCrawlerService;

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final SystemTime systemTime;

    public HighScoreSyncroServiceImpl(HighScoreCrawlerService highScoreCrawlerService,
                                      PlayerRepository playerRepository,
                                      SystemTime systemTime) {
        this.highScoreCrawlerService = highScoreCrawlerService;
        this.playerRepository = playerRepository;
        this.systemTime = systemTime;
    }

    public void synchronizeToDatabase() {
        var playersFromWeb = highScoreCrawlerService.getAllPlayerNamesFromWeb()
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

    private Player convertToModel(PlayerOverallRankTableRow row) {
        var experience = PlayerExperience.builder()
            .level(row.getLevel())
            .rank(row.getRank())
            .xp(row.getXp())
            .build();

        var player = Player.builder()
            .name(row.getPlayer())
            .build();

        player.addExperience(experience);

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

        if (dbPlayer.getClosedAt() == null) {
            logger.debug("Existing player [{}].", dbPlayer.getName());
        } else {
            logger.debug("Existing [CLOSED] player [{}].", dbPlayer.getName());
            dbPlayer.close(null);
            dbPlayer.getExperiences().forEach(it -> it.close(null));
        }

        var experience = webPlayer.getExperiences().stream().findFirst().orElseThrow();
        dbPlayer.addExperience(experience);

        players.add(dbPlayer);
    }
}
