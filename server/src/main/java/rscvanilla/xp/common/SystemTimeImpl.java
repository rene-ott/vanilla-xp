package rscvanilla.xp.common;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class SystemTimeImpl implements SystemTime {

    private final LocalDateTime time;

    private SystemTimeImpl() {
        time = LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public LocalDateTime current() {
        return time;
    }
}
