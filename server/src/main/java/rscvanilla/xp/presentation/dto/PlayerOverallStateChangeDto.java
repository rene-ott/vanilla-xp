package rscvanilla.xp.presentation.dto;

import lombok.Getter;
import lombok.Setter;

public class PlayerOverallStateChangeDto {

    @Getter @Setter
    private String playerName;

    @Getter @Setter
    private Integer xpCurrent;
    @Getter @Setter
    private Integer xpPrevious;
    @Getter @Setter
    private Integer xpChange;

    @Getter @Setter
    private Integer levelCurrent;
    @Getter @Setter
    private Integer levelPrevious;
    @Getter @Setter
    private Integer levelChange;

    @Getter @Setter
    private Integer rankCurrent;
    @Getter @Setter
    private Integer rankPrevious;
    @Getter @Setter
    private Integer rankChange;

    @Getter @Setter
    private boolean isNotAvailable;
}
