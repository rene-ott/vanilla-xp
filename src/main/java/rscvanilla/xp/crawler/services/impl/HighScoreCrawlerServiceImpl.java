package rscvanilla.xp.crawler.services.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import rscvanilla.xp.crawler.models.PlayerOverallRankTableRow;
import rscvanilla.xp.crawler.parsers.PlayerOverallRankTableParser;
import rscvanilla.xp.crawler.services.HighScoreCrawlerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HighScoreCrawlerServiceImpl implements HighScoreCrawlerService {

    private static final String INITIAL_URL = "https://www.runescapeclassic.org/hiscore/ranking/overall";

    private final PlayerOverallRankTableParser playerOverallRankTableParser;

    public HighScoreCrawlerServiceImpl(PlayerOverallRankTableParser playerOverallRankTableParser) {
        this.playerOverallRankTableParser = playerOverallRankTableParser;
    }

    public List<PlayerOverallRankTableRow> getAllPlayerNamesFromWeb() {

        var htmlDocument = loadDocumentFromURL(INITIAL_URL);
        var playerListTable = playerOverallRankTableParser.getTable(htmlDocument);

        var playerNames = new ArrayList<>(playerListTable.getRows());
        while (playerListTable.hasNextPage()) {
            htmlDocument = loadDocumentFromURL(playerListTable.getNextPageUrl());
            playerListTable = playerOverallRankTableParser.getTable(htmlDocument);
            playerNames.addAll(playerListTable.getRows());

            return playerNames;
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
