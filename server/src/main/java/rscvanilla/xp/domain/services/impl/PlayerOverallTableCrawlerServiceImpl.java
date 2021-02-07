package rscvanilla.xp.domain.services.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.domain.models.PlayerOverallTableRow;
import rscvanilla.xp.domain.services.PlayerOverallTableParserService;
import rscvanilla.xp.domain.services.PlayerOverallTableCrawlerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerOverallTableCrawlerServiceImpl implements PlayerOverallTableCrawlerService {

    private static final String INITIAL_URL = "https://www.runescapeclassic.org/hiscore/ranking/overall";

    private final PlayerOverallTableParserService playerOverallTableParserService;

    @Autowired
    public PlayerOverallTableCrawlerServiceImpl(PlayerOverallTableParserService playerOverallTableParserService) {
        this.playerOverallTableParserService = playerOverallTableParserService;
    }

    public List<PlayerOverallTableRow> getAllPlayerNamesFromWeb() {

        var htmlDocument = loadDocumentFromURL(INITIAL_URL);
        var playerListTable = playerOverallTableParserService.getTable(htmlDocument);

        var playerNames = new ArrayList<>(playerListTable.getRows());
        while (playerListTable.hasNextPage()) {
            htmlDocument = loadDocumentFromURL(playerListTable.getNextPageUrl());
            playerListTable = playerOverallTableParserService.getTable(htmlDocument);
            playerNames.addAll(playerListTable.getRows());
        }

        return playerNames;
    }

    private Document loadDocumentFromURL(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            throw new RuntimeException("");
        }
        return doc;
    }
}
