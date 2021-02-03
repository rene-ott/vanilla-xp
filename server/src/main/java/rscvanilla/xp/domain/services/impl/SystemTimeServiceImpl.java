package rscvanilla.xp.domain.services.impl;

import org.springframework.stereotype.Service;
import rscvanilla.xp.domain.services.SystemTimeService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class SystemTimeServiceImpl implements SystemTimeService {

    private final Instant timeStamp;

    private SystemTimeServiceImpl() {
        timeStamp = Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public Instant currentTimeStamp() {
        return timeStamp;
    }

    public LocalDate currentDate() {
        return SystemTimeService.toCurrentDate(timeStamp);
    }
}
