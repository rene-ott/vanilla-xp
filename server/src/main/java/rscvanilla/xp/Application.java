package rscvanilla.xp;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.domain.models.PlayerOverallStateChange;
import rscvanilla.xp.presentation.dto.PlayerOverallStateChangeDto;
import rscvanilla.xp.presentation.dto.SyncroResultDto;
import rscvanilla.xp.presentation.mapper.InstantToLocalDateTimeConverter;
import rscvanilla.xp.presentation.mapper.mappings.SyncroResultDtoMapping;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class Application {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		var mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.addConverter(new InstantToLocalDateTimeConverter());

		mapper.createTypeMap(SyncroResult.class, SyncroResultDto.class).addMappings(new SyncroResultDtoMapping());

		mapper.createTypeMap(PlayerOverallStateChange.class, PlayerOverallStateChangeDto.class);

		mapper.validate();

		return mapper;
	}

	@Bean
	@Profile("dev")
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList(String.format("%s:%s", env.getProperty("dev_server.host"), env.getProperty("dev_server.port"))));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
}
