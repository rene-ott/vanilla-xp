package rscvanilla.xp.domain.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public interface SystemTimeService {
    Instant currentTimeStamp();
    LocalDate currentDate();

    static LocalDate toCurrentDate(Instant timeStamp) {
        return LocalDate.ofInstant(timeStamp, ZoneOffset.UTC);
    }
}
