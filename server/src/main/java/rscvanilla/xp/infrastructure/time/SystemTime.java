package rscvanilla.xp.infrastructure.time;

import java.time.*;
import java.time.temporal.ChronoUnit;

public interface SystemTime {
    Instant currentTimeStamp();
    LocalDate currentDate();

    LocalDateTime currentDateStartOfTheDay();
    LocalDateTime currentDateEndOfTheDay();

    // TODO separate class DateTime
    static LocalDate toCurrentDate(Instant timeStamp) {
        return LocalDate.ofInstant(timeStamp, ZoneOffset.UTC);
    }

    static LocalDateTime toCurrentDateTime(Instant timestamp) {
        return LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    }

    static Instant now() {
        return Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
    }
}
