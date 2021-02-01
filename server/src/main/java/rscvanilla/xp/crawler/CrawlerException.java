package rscvanilla.xp.crawler;

public class CrawlerException extends RuntimeException {
    public CrawlerException(String contextMessage, String exMessage) {
        super(String.format("Context:%s.%sProblem:%s.", contextMessage, System.lineSeparator(), exMessage));
    }
}
