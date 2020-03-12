package no.experis.tbbackend;

import no.experis.tbbackend.Models.User;
import no.experis.tbbackend.Repositories.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TbBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TbBackEndApplication.class, args);

		UserRepo userRepo = new UserRepo();
		User user1 = new User("JohnWick", "null", "lol", true);

		userRepo.save(user1);

	}
}
