package no.experis.tbbackend.Controllers;

import no.experis.tbbackend.Exception.ResourceNotFoundException;
import no.experis.tbbackend.Models.User;
import no.experis.tbbackend.Repositories.UserRepo;
import no.experis.tbbackend.Security.oauth2.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllers {
    @Autowired
    private UserRepo userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
