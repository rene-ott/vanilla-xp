package rscvanilla.xp.domain.models;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PlayerOverallTableRow {

    @Getter
    private final int rank;

    @Getter
    private final String player;

    @Getter
    private final int level;

    @Getter
    private final int xp;

    public static class PlayerOverallTableRowBuilder {
        private int level;
        private int xp;
        private int rank;

        public PlayerOverallTableRowBuilder level(String level) {
            this.level = convertToInt(level);
            return this;
        }

        public PlayerOverallTableRowBuilder rank(String rank) {
            this.rank = convertToInt(rank);
            return this;
        }

        public PlayerOverallTableRowBuilder xp(String xp) {
            this.xp = convertToInt(xp);
            return this;
        }

        private int convertToInt(String val) {
            return Integer.parseInt(val.replace(",", ""));
        }
    }
}
