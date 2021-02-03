package rscvanilla.xp.infrastructure.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "scheduling", name="enabled", havingValue="true", matchIfMissing = true)
public class SchedulerConfig {
}
