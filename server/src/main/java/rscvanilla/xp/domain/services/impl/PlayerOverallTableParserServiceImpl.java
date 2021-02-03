package rscvanilla.xp.domain.services.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rscvanilla.xp.infrastructure.SyncroException;
import rscvanilla.xp.domain.models.PlayerOverallTable;
import rscvanilla.xp.domain.models.PlayerOverallTableRow;
import rscvanilla.xp.domain.services.PlayerOverallTableParserService;

@Service
public class PlayerOverallTableParserServiceImpl implements PlayerOverallTableParserService {

    private final Logger logger = LoggerFactory.getLogger(PlayerOverallTableParserServiceImpl.class);

    @Override
    public PlayerOverallTable getTable(Document document) {

        var paginationCtxMessage = "Selecting pagination element from player list table";
        var paginationElement = selectSingleElementFrom(document, "#hiscore .pagination", paginationCtxMessage);

        var currentPageNumberValue = getCurrentPageNumberValue(paginationElement, paginationCtxMessage);
        var nextPageUrl = getNextPageUrl(paginationElement, paginationCtxMessage);

        var playerListTable = PlayerOverallTable.builder()
                .nextPageUrl(nextPageUrl);

        var tableRows = document.select("#hiscore .hiscore_table tr");

        for (var rowIndex = 1; rowIndex < tableRows.size(); rowIndex++) {
            var row = tableRows.get(rowIndex);

            var playerRow = PlayerOverallTableRow.builder()
                .rank(getColumnValue(row, currentPageNumberValue, rowIndex, 1, "Rank"))
                .player(getColumnValue(row, currentPageNumberValue, rowIndex, 2, "Player"))
                .level(getColumnValue(row, currentPageNumberValue, rowIndex, 3, "Level"))
                .xp(getColumnValue(row, currentPageNumberValue, rowIndex, 4, "XP"))
                .build();

            var playerIndex = ((currentPageNumberValue -1) * tableRows.size() - 1) + rowIndex;
            logger.debug("Crawled row {}) [rank={}, player={}, level={}, xp={}].", playerIndex, playerRow.getRank(), playerRow.getPlayer(), playerRow.getLevel(), playerRow.getXp());
            playerListTable.row(playerRow);
        }

        return playerListTable.build();
    }

    private String getColumnValue(Element tableRow, int pageNumber, int rowNumber, int columnNumber, String columnName) {
        var ctxMessage = String.format("Selecting column [%s] from page [%d] table row [%d]", columnName, pageNumber, rowNumber);
        var columnElement = selectSingleElementFrom(tableRow, String.format(".col%d a", columnNumber), ctxMessage);
        var columnElementValue = columnElement.text();

        if (columnElementValue.equals("")) {
            throw new SyncroException(ctxMessage, "Missing ["+ columnName +"] for row");
        }

        return columnElementValue;
    }

    private String getNextPageUrl(Element paginationElement, String paginationCtx) {
        var nextLink = selectSingleOrNullElementFrom(paginationElement, "a[rel]", paginationCtx);

        if (nextLink == null) {
            return "";
        }

        var nextLinkHref = nextLink.attr("href");
        if (nextLinkHref != null && !new UrlValidator().isValid(nextLinkHref)) {
            throw new SyncroException(paginationCtx, "NextLink URL is invalid ["+ nextLinkHref +"]");
        }

        return nextLinkHref;
    }

    private int getCurrentPageNumberValue(Element paginationElement, String paginationCtx) {
        var currentPageNumberElement = selectSingleElementFrom(paginationElement, ".active .text", paginationCtx);
        var currentPageNumberValue = currentPageNumberElement.text();
        if (currentPageNumberValue.equals("")) {
            throw new SyncroException(paginationCtx, "CurrentPageNumber value is missing");
        }

        return Integer.parseInt(currentPageNumberValue);
    }

    private static Element selectSingleOrNullElementFrom(Element element, String selector, String ctxMessage) {
        return selectSingleElementFrom(element, selector, ctxMessage, true);
    }

    private static Element selectSingleElementFrom(Element element, String selector, String ctxMessage) {
        return selectSingleElementFrom(element, selector, ctxMessage, false);
    }

    private static Element selectSingleElementFrom(Element element, String selector, String ctxMessage, boolean isNullEnabled) {
        var elements = element.select(selector);
        if (!isNullEnabled && elements.isEmpty()) {
            throw new SyncroException(ctxMessage, "Couldn't find single element (found 0) with selector ["+ selector +"]");
        } else if (elements.size() > 1) {
            throw new SyncroException(ctxMessage, "Couldn't find single element (found"+ elements.size() +") with selector ["+ selector +"]");
        }

        return elements.first();
    }
}
