package rscvanilla.xp.crawler.parsers;

import org.jsoup.nodes.Document;
import rscvanilla.xp.crawler.models.PlayerOverallRankTable;

public interface PlayerOverallRankTableParser {
    PlayerOverallRankTable getTable(Document document);
}
