package rscvanilla.xp.crawler.models;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PlayerOverallRankTableRow {

    @Getter
    private final int rank;

    @Getter
    private final String player;

    @Getter
    private final int level;

    @Getter
    private final int xp;

    public static class PlayerOverallRankTableRowBuilder {
        private int level;
        private int xp;
        private int rank;

        public PlayerOverallRankTableRowBuilder level(String level) {
            this.level = convertToInt(level);
            return this;
        }

        public PlayerOverallRankTableRowBuilder rank(String rank) {
            this.rank = convertToInt(rank);
            return this;
        }

        public PlayerOverallRankTableRowBuilder xp(String xp) {
            this.xp = convertToInt(xp);
            return this;
        }

        private int convertToInt(String val) {
            return Integer.parseInt(val.replace(",", ""));
        }
    }
}
