package rscvanilla.xp.infrastructure.time;

import java.time.Instant;

public interface SystemTimeContext {
    void clearTime();
    void setTime(Instant instant);
    Instant getTime();
}
