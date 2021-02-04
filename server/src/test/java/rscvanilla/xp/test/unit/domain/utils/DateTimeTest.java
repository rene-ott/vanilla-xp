package rscvanilla.xp.test.unit.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rscvanilla.xp.domain.utils.DateTime;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class DateTimeTest {

    @Test
    public void toDate_timestamp_returnsDate() {
        var utcTimeStamp = createUtcInstant("2020-01-14T23:59:00Z");

        var localDate = DateTime.toDate(utcTimeStamp);

        assertThat(localDate).isEqualTo(LocalDate.of(2020, 1, 14));
    }

    private Instant createUtcInstant(String utcTimestamp) {
        return Instant.now(Clock.fixed(Instant.parse(utcTimestamp), ZoneOffset.UTC));
    }
}
