package rscvanilla.xp.web.dto;

import lombok.Getter;
import lombok.Setter;

public class PlayerExperienceDeltaDto {

    @Getter @Setter
    private String playerName;

    @Getter @Setter
    private Integer currentXp;
    @Getter @Setter
    private Integer previousXp;
    @Getter @Setter
    private Integer deltaXp;

    @Getter @Setter
    private Integer currentLevel;

    @Getter @Setter
    private Integer previousLevel;

    @Getter @Setter
    private Integer deltaLevel;

    @Getter @Setter
    private Integer currentRank;
    @Getter @Setter
    private Integer previousRank;
    @Getter @Setter
    private Integer deltaRank;

    @Getter @Setter
    private boolean isNotAvailable;
}
