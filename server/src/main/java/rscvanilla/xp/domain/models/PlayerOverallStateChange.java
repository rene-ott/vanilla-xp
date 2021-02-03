package rscvanilla.xp.domain.models;

import rscvanilla.xp.domain.entities.PlayerOverallState;

public class PlayerOverallStateChange {

    private final String playerName;

    private final PlayerOverallState previous;
    private final PlayerOverallState current;

    private PlayerOverallStateChange(String playerName, PlayerOverallState previous, PlayerOverallState current) {
        this.playerName = playerName;
        this.previous = previous;
        this.current = current;
    }

    public boolean isNotAvailable() { return current == null || previous == null; }

    public Integer getRankChange() { return isNotAvailable() ? null :  current.getRank() - previous.getRank(); }
    public Integer getRankCurrent() { return isNotAvailable() ? null : current.getRank(); }
    public Integer getRankPrevious() { return isNotAvailable() ? null : previous.getRank(); }

    public Integer getXpChange() { return isNotAvailable() ? null : current.getXp() - previous.getXp(); }
    public Integer getXpCurrent() { return isNotAvailable() ? null : current.getXp(); }
    public Integer getXpPrevious() { return isNotAvailable() ? null : previous.getXp(); }

    public Integer getLevelChange() { return isNotAvailable() ? null : current.getLevel() - previous.getLevel(); }
    public Integer getLevelCurrent() { return isNotAvailable() ? null : current.getLevel(); }
    public Integer getLevelPrevious() { return isNotAvailable() ? null : previous.getLevel(); }

    public String getPlayerName() { return playerName; }

    public static PlayerOverallStateChange createEmpty(String playerName) {
        return new PlayerOverallStateChange(playerName, null, null);
    }

    public static PlayerOverallStateChange createComplete(String playerName, PlayerOverallState previous, PlayerOverallState current) {
        return new PlayerOverallStateChange(playerName, previous, current);
    }
}
