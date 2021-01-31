package rscvanilla.xp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import rscvanilla.xp.crawler.services.HighScoreSyncroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private HighScoreSyncroService highScoreSyncroService;

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
	}

	@Override
	public void run(String... args) {
		highScoreSyncroService.synchronizeToDatabase();
	}
}
