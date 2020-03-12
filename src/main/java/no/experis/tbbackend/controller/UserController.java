package no.experis.tbbackend.controller;

import no.experis.tbbackend.exception.ResourceNotFoundException;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/me")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAdminCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/me")
    public User getCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("CALLING /USER/ME");
        return  userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}

/*
 * userRepository.findById(userPrincipal.getId())
 *                 .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
 *
*/