package rscvanilla.xp.domain.services;

import org.jsoup.nodes.Document;
import rscvanilla.xp.domain.models.PlayerOverallTable;

public interface PlayerOverallTableParserService {
    PlayerOverallTable getTable(Document document);
}
