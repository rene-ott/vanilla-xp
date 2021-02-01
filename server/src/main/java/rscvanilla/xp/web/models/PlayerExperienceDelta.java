package rscvanilla.xp.web.models;

public class PlayerExperienceDelta {

    private final String playerName;

    private final PlayerExperience previous;
    private final PlayerExperience current;

    private PlayerExperienceDelta(String playerName, PlayerExperience previous, PlayerExperience current) {
        this.playerName = playerName;
        this.previous = previous;
        this.current = current;
    }

    public boolean isNotAvailable() { return current == null || previous == null; }

    public Integer getDeltaRank() { return isNotAvailable() ? null :  current.getRank() - previous.getRank(); }
    public Integer getCurrentRank() { return isNotAvailable() ? null : current.getRank(); }
    public Integer getPreviousRank() { return isNotAvailable() ? null : previous.getRank(); }

    public Integer getDeltaXp() { return isNotAvailable() ? null : current.getXp() - previous.getXp(); }
    public Integer getCurrentXp() { return isNotAvailable() ? null : current.getXp(); }
    public Integer getPreviousXp() { return isNotAvailable() ? null : previous.getXp(); }

    public Integer getDeltaLevel() { return isNotAvailable() ? null : current.getLevel() - previous.getLevel(); }
    public Integer getCurrentLevel() { return isNotAvailable() ? null : current.getLevel(); }
    public Integer getPreviousLevel() { return isNotAvailable() ? null : previous.getLevel(); }

    public String getPlayerName() { return playerName; }

    public static PlayerExperienceDelta createEmpty(String playerName) {
        return new PlayerExperienceDelta(playerName, null, null);
    }

    public static PlayerExperienceDelta createComplete(String playerName, PlayerExperience previous, PlayerExperience current) {
        return new PlayerExperienceDelta(playerName, previous, current);
    }
}
