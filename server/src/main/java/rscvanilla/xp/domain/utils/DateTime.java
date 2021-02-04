package rscvanilla.xp.domain.utils;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateTime {
    public static LocalDate toDate(Instant timeStamp) {
        return LocalDate.ofInstant(timeStamp, ZoneOffset.UTC);
    }

    public static LocalDateTime toDateTime(Instant timestamp) {
        return LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    }

    public static Instant now() {
        return Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
    }
}
