package rscvanilla.xp.infrastructure;

public class SyncroException extends RuntimeException {
    public SyncroException(String contextMessage, String exMessage) {
        super(String.format("Context:%s.%sProblem:%s.", contextMessage, System.lineSeparator(), exMessage));
    }
}
