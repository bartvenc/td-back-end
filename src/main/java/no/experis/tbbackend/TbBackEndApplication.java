package no.experis.tbbackend;

import no.experis.tbbackend.Configurations.AppProperties;
import no.experis.tbbackend.Models.User;
import no.experis.tbbackend.Repositories.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TbBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TbBackEndApplication.class, args);

		UserRepo userRepo = new UserRepo();
		User user1 = new User("JohnWick", "null", true);

		userRepo.save(user1);

	}
}
