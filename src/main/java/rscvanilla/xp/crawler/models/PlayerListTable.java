package rscvanilla.xp.crawler.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class PlayerListTable {

    @Getter @Singular
    private final List<String> playerNames;

    public boolean hasNextPage() {
        return !nextPageUrl.equals("");
    }

    @Getter
    private final String nextPageUrl;
}
