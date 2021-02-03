package rscvanilla.xp.domain.services;

import rscvanilla.xp.domain.models.PlayerOverallTableRow;

import java.util.List;

public interface PlayerOverallTableCrawlerService {
    List<PlayerOverallTableRow> getAllPlayerNamesFromWeb();
}
