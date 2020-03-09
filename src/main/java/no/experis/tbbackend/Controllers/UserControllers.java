package no.experis.tbbackend.Controllers;

import no.experis.tbbackend.Repositories.UserRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllers {
    UserRepo userRepo = new UserRepo();


}
