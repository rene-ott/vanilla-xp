package rscvanilla.xp;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rscvanilla.xp.web.dto.PlayerExperienceDeltaDto;
import rscvanilla.xp.web.models.PlayerExperienceDelta;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private HighScoreSyncroService highScoreSyncroService;

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
}
