package rscvanilla.xp.infrastructure.time;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class SystemTimeImpl implements SystemTime {

    private final SystemTimeContext timeContext;

    public SystemTimeImpl(SystemTimeContext timeContext) {
        this.timeContext = timeContext;
    }

    @Override
    public Instant currentTimeStamp() {
        return timeContext.getTime();
    }

    @Override
    public LocalDate currentDate() {
        return SystemTime.toCurrentDate(timeContext.getTime());
    }

    @Override
    public LocalDateTime currentDateStartOfTheDay() {
        return currentDate().atStartOfDay();
    }

    @Override
    public LocalDateTime currentDateEndOfTheDay() {
        return LocalTime.MAX.atDate(currentDate());
    }
}
