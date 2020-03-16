package no.experis.tbbackend.controller;

import net.minidev.json.JSONObject;
import no.experis.tbbackend.exception.ResourceNotFoundException;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.repository.UserRepo;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.repository.VacationRequestRepo;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String getCurrentUserProfile(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("/USER");
        return "http://localhost:8080/user" + userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/admin/me")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAdminCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("calling /admin/me");
        return userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/me")
    public User getCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("CALLING /USER/ME");
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }


    @GetMapping("admin/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserAsAdmin(@PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        System.out.println("calling admin/user/{ID}");
        UserRepo userRepo = new UserRepo();
        User returnUser = userRepository.findById(id);
        JSONObject object = new JSONObject();
        if (returnUser != null) {

            object.put("ID", returnUser.getId());
            object.put("name", returnUser.getName());
            object.put("email", returnUser.getEmail());
            object.put("imageUrl", returnUser.getImageUrl());

            response.setStatus(200);
            return object.toJSONString();

        }
        response.sendError(200, "User not found");
        return "User not found";
    }

    @GetMapping("/user/{id}")
    public String getUserAsUser(@PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        System.out.println("calling user/{ID}");
        UserRepo userRepo = new UserRepo();
        User returnUser = userRepository.findById(id);
        JSONObject object = new JSONObject();
        if (returnUser != null) {

            object.put("ID", returnUser.getId());
            object.put("name", returnUser.getName());
            object.put("imageUrl", returnUser.getImageUrl());

            response.setStatus(200);
            return object.toJSONString();

        }
        response.sendError(200, "User not found");
        return "User not found";
    }

    @GetMapping("/user/{id}/requests")
    public List<VacationRequest> getUserRequests(@PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        System.out.println("calling /user/{ID}/requests  ");
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        UserRepo userRepo = new UserRepo();
        List<VacationRequest> vacationRequests = (List<VacationRequest>) new HashSet<VacationRequest>();
        User returnUser = userRepository.findById(id);
        if (returnUser != null) {
            vacationRequests = vacationRequestRepo.findAllByUserID(returnUser.getId().intValue());
            if (vacationRequests != null) {
                response.setStatus(200);
                return vacationRequests;
            } else {
                response.sendError(400, "user has no requests");
                return null;
            }
        } else {
            response.sendError(400, "user not found");
            return null;
        }
    }


}

