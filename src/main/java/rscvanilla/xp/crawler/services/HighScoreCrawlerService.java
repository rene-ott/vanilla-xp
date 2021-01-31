package rscvanilla.xp.crawler.services;

import rscvanilla.xp.crawler.models.PlayerOverallRankTableRow;

import java.util.List;

public interface HighScoreCrawlerService {
    List<PlayerOverallRankTableRow> getAllPlayerNamesFromWeb();
}
