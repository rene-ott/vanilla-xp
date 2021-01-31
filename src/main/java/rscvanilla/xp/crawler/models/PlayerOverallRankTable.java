package rscvanilla.xp.crawler.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class PlayerOverallRankTable {

    @Getter @Singular
    private final List<PlayerOverallRankTableRow> rows;

    public boolean hasNextPage() {
        return !nextPageUrl.equals("");
    }

    @Getter
    private final String nextPageUrl;
}
