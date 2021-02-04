package rscvanilla.xp.test.unit.infrastructure.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.infrastructure.time.SystemTimeContext;
import rscvanilla.xp.infrastructure.time.SystemTimeImpl;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SystemTimeImplTest {

    private SystemTime systemTime;

    @Mock
    private SystemTimeContext systemTimeContext;

    @BeforeEach
    public void setUp() {
        systemTime = new SystemTimeImpl(systemTimeContext);
    }

    @Test
    public void toCurrentDate_timestamp_returnsDate() {
        var utcTimeStamp = createUtcInstant("2020-01-14T23:59:00Z");

        var localDate = SystemTime.toCurrentDate(utcTimeStamp);

        assertThat(localDate).isEqualTo(LocalDate.of(2020, 1, 14));
    }

    @Test
    public void currentDateEndOfTheDay_timestamp_returnsWithMaxTime() {
        when(systemTimeContext.getTime()).thenReturn(createUtcInstant("2020-01-14T22:59:00Z"));

        var result = systemTime.currentDateEndOfTheDay();

        assertThat(result).isEqualTo(LocalDateTime.of(2020, 1, 14, 23, 59, 59, 999999999));
    }

    @Test
    public void currentDateStartOfTheDay_timestamp_returnsWithMinTime() {
        when(systemTimeContext.getTime()).thenReturn(createUtcInstant("2020-01-14T22:59:00Z"));

        var result = systemTime.currentDateStartOfTheDay();

        assertThat(result).isEqualTo(LocalDateTime.of(2020, 1, 14, 0, 0, 0, 0));
    }

    private Instant createUtcInstant(String utcTimestamp) {
        return Instant.now(Clock.fixed(Instant.parse(utcTimestamp), ZoneOffset.UTC));
    }
}
