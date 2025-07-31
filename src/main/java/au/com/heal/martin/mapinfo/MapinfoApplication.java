package au.com.heal.martin.mapinfo;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class MapinfoApplication implements CommandLineRunner {

	private DataSource dataSource;

	public MapinfoApplication(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(MapinfoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Datasource: " + dataSource.toString());

		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}

}
