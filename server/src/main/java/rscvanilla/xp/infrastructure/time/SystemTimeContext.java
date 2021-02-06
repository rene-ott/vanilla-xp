package rscvanilla.xp.infrastructure.time;

import java.time.Instant;

public interface SystemTimeContext {
    void clearTempTime();
    void setTempTime(Instant instant);
    Instant getTime();

    void setRequestTime(Instant instant);
    void clearRequestTime();
}
