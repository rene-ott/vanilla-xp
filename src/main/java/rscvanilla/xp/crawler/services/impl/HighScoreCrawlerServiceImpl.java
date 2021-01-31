package rscvanilla.xp.crawler.services.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rscvanilla.xp.crawler.services.HighScoreCrawlerService;
import rscvanilla.xp.web.models.PlayerExperience;
import rscvanilla.xp.crawler.parsers.PlayerListTableParser;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class HighScoreCrawlerServiceImpl implements HighScoreCrawlerService {

    private final String baseUrl = "https://www.runescapeclassic.org/hiscore/ranking";

    @Autowired
    private final PlayerListTableParser playerListTableParser;

    public HighScoreCrawlerServiceImpl(PlayerListTableParser playerListTableParser) {
        this.playerListTableParser = playerListTableParser;
    }

    public List<String> getAllPlayerNamesFromWeb() {

        var htmlDocument = getDocumentFromUrl(getPlayerListTableUrl());
        var playerListTable = playerListTableParser.getPlayerListTable(htmlDocument);

        var playerNames = new ArrayList<>(playerListTable.getPlayerNames());
        /*
        while (playerListTable.hasNextPage()) {
            htmlDocument = getDocumentFromUrl(playerListTable.getNextPageUrl());
            playerListTable = playerListTableParser.getPlayerListTable(htmlDocument);
        }*/

        return playerNames;
    }

    private String getPlayerListTableUrl() {
        return String.format("%s/overall", baseUrl);
    }

    private String getPlayerExperienceTableUrl(String playerName) {
        var queryStringParams = URLEncoder.encode(playerName, StandardCharsets.UTF_8);
        return String.format("%s/user=%s", baseUrl, queryStringParams);
    }

    private Document getDocumentFromUrl(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            throw new RuntimeException("");
        }
        return doc;
    }

    public PlayerExperience getPlayerExperience(String name) {
        return null;
    }
}
