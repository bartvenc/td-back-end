package no.experis.tbbackend.controller;

import no.experis.tbbackend.exception.ResourceNotFoundException;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private UserRepository userRepo;

   /* @GetMapping("/admin/me")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAdminCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
*/
   @GetMapping("/user/me")
    public User getCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

   @GetMapping("/user/{id}")
   public User getUserById(@PathVariable(value="id") long id){
       System.out.println("Calling /user/{ID}");
       return userRepository.findById(id);
   }
}

