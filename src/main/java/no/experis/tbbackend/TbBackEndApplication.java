package no.experis.tbbackend;

import no.experis.tbbackend.config.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TbBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TbBackEndApplication.class, args);

		///Link to login via Google:
		// http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:3000/oauth2/redirect
	}
}
