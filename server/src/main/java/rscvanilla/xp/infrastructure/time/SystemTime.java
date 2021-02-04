package rscvanilla.xp.infrastructure.time;

import java.time.*;

public interface SystemTime {
    Instant currentTimeStamp();
    LocalDate currentDate();

    LocalDateTime currentDateStartOfTheDay();
    LocalDateTime currentDateEndOfTheDay();
}
