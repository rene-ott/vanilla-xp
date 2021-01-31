package rscvanilla.xp.crawler.parsers;

import org.jsoup.nodes.Document;
import rscvanilla.xp.crawler.models.PlayerListTable;

public interface PlayerListTableParser {
    PlayerListTable getPlayerListTable(Document document);
}
