package rscvanilla.xp.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public interface SystemTime {
    Instant currentTimeStamp();
    LocalDate currentDate();

    static LocalDate toCurrentDate(Instant timeStamp) {
        return LocalDate.ofInstant(timeStamp, ZoneOffset.UTC);
    }
}
