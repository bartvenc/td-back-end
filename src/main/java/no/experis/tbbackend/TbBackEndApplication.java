package no.experis.tbbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TbBackEndApplication {

	public static void main(String[] args) {
        SpringApplication.run(TbBackEndApplication.class, args);

        //UserRepo userRepo = new UserRepo();
        //User user1 = new User("JohnWick", "null", true);

        //userRepo.save(user1);

    }
}
