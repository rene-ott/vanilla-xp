package rscvanilla.xp.crawler.services.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.common.SystemTime;
import rscvanilla.xp.crawler.services.HighScoreCrawlerService;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import rscvanilla.xp.web.models.Player;
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
                .map(it -> Player.builder().name(it).build())
                .collect(Collectors.toList());

        var playersFromDatabase = ImmutableList.copyOf(playerRepository.findAll());

        var updatablePlayers = new ArrayList<Player>();

        for (var webPlayer : playersFromWeb) {
            playersFromDatabase.stream()
                    .filter(dbPlayer -> dbPlayer.hasSameName(webPlayer))
                    .findFirst()
                    .ifPresentOrElse(
                        dbPlayer -> updatePlayer(dbPlayer, updatablePlayers),
                        () -> createPlayer(webPlayer, updatablePlayers)
                    );
        }

        for (var dbPlayer : playersFromDatabase) {
            playersFromWeb.stream()
                    .filter(webPlayer -> webPlayer.hasSameName(dbPlayer))
                    .findFirst()
                    .ifPresentOrElse(player -> {}, () -> closePlayer(dbPlayer, updatablePlayers));
        }

        playerRepository.saveAll(playersFromWeb);
    }
    
    private void closePlayer(Player existingPlayer, List<Player> updatablePlayers) {
        logger.debug("Removed player [{}].", existingPlayer.getName());
        existingPlayer.close(systemTime.current());
        updatablePlayers.add(existingPlayer);
    }

    private void createPlayer(Player newPlayer, List<Player> players) {
        logger.debug("New player [{}].", newPlayer.getName());
        players.add(newPlayer);
    }

    private void updatePlayer(Player existingPlayer, List<Player> players) {
        logger.debug("Existing player [{}].", existingPlayer.getName());
        players.add(existingPlayer);
    }
}
