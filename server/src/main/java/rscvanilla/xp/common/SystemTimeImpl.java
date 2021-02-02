package rscvanilla.xp.common;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Component
public class SystemTimeImpl implements SystemTime {

    private final Instant timeStamp;

    private SystemTimeImpl() {
        timeStamp = Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public Instant currentTimeStamp() {
        return timeStamp;
    }

    public LocalDate currentDate() {
        return SystemTime.toCurrentDate(timeStamp);
    }
}
