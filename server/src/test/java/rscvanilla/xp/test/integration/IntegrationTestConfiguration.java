package rscvanilla.xp.test.integration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import rscvanilla.xp.domain.services.SystemTimeService;

@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean
    @Primary
    public SystemTimeService systemTimeService() {
        return Mockito.mock(SystemTimeService.class);
    }
}
