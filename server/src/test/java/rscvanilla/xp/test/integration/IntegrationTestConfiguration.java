package rscvanilla.xp.test.integration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import rscvanilla.xp.infrastructure.time.SystemTime;

@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean
    @Primary
    public SystemTime systemTime() {
        return Mockito.mock(SystemTime.class);
    }
}
