package rscvanilla.xp.crawler.parsers.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rscvanilla.xp.crawler.CrawlerException;
import rscvanilla.xp.crawler.models.PlayerListTable;
import rscvanilla.xp.crawler.parsers.PlayerListTableParser;

@Service
public class PlayerListTableParserImpl implements PlayerListTableParser {

    private Logger logger = LoggerFactory.getLogger(PlayerListTableParserImpl.class);

    @Override
    public PlayerListTable getPlayerListTable(Document document) {

        var paginationCtxMessage = "Selecting pagination element from player list table";
        var paginationElement = selectSingleElementFrom(document, "#hiscore .pagination", paginationCtxMessage);

        var currentPageNumberValue = getCurrentPageNumberValue(paginationElement, paginationCtxMessage);
        var nextPageUrl = getNextPageUrl(paginationElement, paginationCtxMessage);

        var playerListTable = PlayerListTable.builder()
                .nextPageUrl(nextPageUrl);

        var tableRows = document.select("#hiscore .hiscore_table tr");

        for (var rowIndex = 1; rowIndex < tableRows.size(); rowIndex++) {
            var playerName = getPlayerName(tableRows.get(rowIndex), currentPageNumberValue, rowIndex);
            var playerIndex = rowIndex * currentPageNumberValue;
            logger.debug("Crawled player ({}) [{}].", playerIndex, playerName);
            playerListTable.playerName(playerName);
        }

        return playerListTable.build();
    }

    private String getPlayerName(Element tableRow, int pageNumber, int row) {
        var ctxMessage = String.format("Selecting player name from page [%s] player list table row [%d]", pageNumber, row);
        var columnElement = selectSingleElementFrom(tableRow, ".col2 a", ctxMessage);
        var columnElementValue = columnElement.text();

        if (columnElementValue.equals("")) {
            throw new CrawlerException(ctxMessage, "Missing player name for row");
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
            throw new CrawlerException(paginationCtx, "NextLink URL is invalid ["+ nextLinkHref +"]");
        }

        return nextLinkHref;
    }

    private int getCurrentPageNumberValue(Element paginationElement, String paginationCtx) {
        var currentPageNumberElement = selectSingleElementFrom(paginationElement, ".active .text", paginationCtx);
        var currentPageNumberValue = currentPageNumberElement.text();
        if (currentPageNumberValue.equals("")) {
            throw new CrawlerException(paginationCtx, "CurrentPageNumber value is missing");
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
            throw new CrawlerException(ctxMessage, "Couldn't find single element (found 0) with selector ["+ selector +"]");
        } else if (elements.size() > 1) {
            throw new CrawlerException(ctxMessage, "Couldn't find single element (found"+ elements.size() +") with selector ["+ selector +"]");
        }

        return elements.first();
    }
}
