package rscvanilla.xp.crawler.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.crawler.services.HighScoreCrawlerService;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import rscvanilla.xp.web.models.Player;
import rscvanilla.xp.web.repositories.PlayerRepository;

import java.util.stream.Collectors;

@Service
public class HighScoreSyncroServiceImpl implements HighScoreSyncroService {

    @Autowired
    private final HighScoreCrawlerService highScoreCrawlerService;

    @Autowired
    private final PlayerRepository playerRepository;

    public HighScoreSyncroServiceImpl(HighScoreCrawlerService highScoreCrawlerService, PlayerRepository playerRepository) {
        this.highScoreCrawlerService = highScoreCrawlerService;
        this.playerRepository = playerRepository;
    }

    public void synchronizeToDatabase() {
        var playerNamesFromWeb = highScoreCrawlerService.getAllPlayerNamesFromWeb()
                .stream()
                .map(it -> Player.builder().name(it).build())
                .collect(Collectors.toList());

        playerRepository.findAll();

        playerRepository.saveAll(playerNamesFromWeb);
    }
}
