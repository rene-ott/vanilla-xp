package rscvanilla.xp.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SyncroResultDto {

    @Getter @Setter
    private LocalDateTime lastCheckDateTime;

    @Getter @Setter
    private LocalDateTime initialCheckDateTime;

    @Getter @Setter
    private String state;

    @Getter @Setter
    private int tryCount;
}
