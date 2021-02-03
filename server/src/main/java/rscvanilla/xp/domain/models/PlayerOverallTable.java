package rscvanilla.xp.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class PlayerOverallTable {

    @Getter @Singular
    private final List<PlayerOverallTableRow> rows;

    public boolean hasNextPage() {
        return !nextPageUrl.equals("");
    }

    @Getter
    private final String nextPageUrl;
}
