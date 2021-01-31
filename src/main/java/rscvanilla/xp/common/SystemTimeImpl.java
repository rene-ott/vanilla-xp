package rscvanilla.xp.common;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class SystemTimeImpl implements SystemTime {

    private LocalDateTime time;

    protected SystemTimeImpl() {
        time = LocalDateTime.now(Clock.systemUTC());
    }

    @Override
    public LocalDateTime current() {
        return time;
    }
}
