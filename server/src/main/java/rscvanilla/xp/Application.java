package rscvanilla.xp;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rscvanilla.xp.web.configuration.DevServerConfiguration;
import rscvanilla.xp.web.dto.PlayerExperienceDeltaDto;
import rscvanilla.xp.web.models.PlayerExperienceDelta;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private HighScoreSyncroService highScoreSyncroService;

	@Autowired
	private DevServerConfiguration devServerConfiguration;

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Override
	public void run(String... args) {
		//highScoreSyncroService.synchronizeToDatabase();
	}

	@Bean
	public ModelMapper getModelMapper() {
		var mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.validate();

		mapper.createTypeMap(PlayerExperienceDelta.class, PlayerExperienceDeltaDto.class);

		return mapper;
	}

	@Bean
	@Profile("dev")
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList(devServerConfiguration.getAddress()));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
}
