package rscvanilla.xp.test.integration.persistance.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rscvanilla.xp.Application;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.domain.models.SyncroResultStatus;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.persistance.repositories.SyncroResultRepository;
import rscvanilla.xp.test.integration.IntegrationTestConfiguration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ContextConfiguration(classes = Application.class)
@Import({IntegrationTestConfiguration.class})
public class SyncroResultRepositoryTest {

    @Autowired
    private SyncroResultRepository syncroResultRepository;

    @MockBean
    private SystemTime systemTime;

    @Test
    public void findAllByCreatedAtBetween_threeResultsTwoInBetween_returnsBoth() {
        var begin = createUtcInstant("2020-01-15T00:00:00Z");
        var end = createUtcInstant("2020-01-15T23:59:00Z");

        var firstResultInstant = createUtcInstant("2020-01-15T00:00:00Z");
        var firstResult = createSyncroResult(firstResultInstant);

        var secondResultInstant = createUtcInstant("2020-01-15T23:59:00Z");
        var secondResult = createSyncroResult(secondResultInstant);

        var thirdResultInstant = createUtcInstant("2020-01-14T23:59:00Z");
        var thirdResult = createSyncroResult(thirdResultInstant);

        // Mock AuditEntityListener SystemTime
        when(systemTime.currentTimeStamp()).thenReturn(
            firstResultInstant, firstResultInstant,
            secondResultInstant, secondResultInstant,
            thirdResultInstant, thirdResultInstant
        );

        syncroResultRepository.saveAll(List.of(firstResult, secondResult, thirdResult));

        var results = syncroResultRepository.findAllBetween(begin, end);

        assertThat(results.size()).isEqualTo(2);

        var retrievedSecondResult = results.get(0);
        assertThat(retrievedSecondResult.getCreatedAt().equals(secondResultInstant));

        var retrievedFirstResult = results.get(1);
        assertThat(retrievedFirstResult.getCreatedAt().equals(firstResultInstant));
    }

    private SyncroResult createSyncroResult(Instant utcTimestamp) {
        var entity = SyncroResult.builder()
            .status(SyncroResultStatus.OK)
            .build();

        return entity;
    }

    private Instant createUtcInstant(String utcTimestamp) {
        return Instant.now(Clock.fixed(Instant.parse(utcTimestamp), ZoneOffset.UTC));
    }
}
