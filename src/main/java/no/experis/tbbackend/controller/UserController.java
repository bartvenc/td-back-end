package no.experis.tbbackend.controller;

import com.google.gson.Gson;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /**
     * Get all users list.
     *
     * @return the list
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets current user profile.
     *
     * @param userPrincipal the user principal
     * @return the current user profile
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public String getCurrentUserProfile(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("/USER");
        return "https://infinite-tundra-25891.herokuapp.com/user" + userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    /**
     * Gets admin current user email.
     *
     * @param userPrincipal the user principal
     * @return the admin current user email
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/admin/me")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAdminCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("calling /admin/me");
        return userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    /**
     * Gets current user email.
     *
     * @param userPrincipal the user principal
     * @return the current user email
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/me")
    public User getCurrentUserEmail(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("CALLING /USER/ME");
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    /**
     * Gets user as admin.
     *
     * @param id       the id
     * @param response the response
     * @return the user as admin
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("admin/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserAsAdmin(@PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        System.out.println("calling admin/user/{ID}");
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

    /**
     * Gets user as user.
     *
     * @param id       the id
     * @param response the response
     * @return the user as user
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    /**
     * Gets user requests.
     *
     * @param id       the id
     * @param response the response
     * @return the user requests
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{id}/requests")
    public List<VacationRequest> getUserRequests(@PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        System.out.println("calling /user/{ID}/requests  ");
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        List<VacationRequest> vacationRequests;
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

    @CrossOrigin(origins="*", allowedHeaders="*")
    @PatchMapping("/user/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public void editUser(@PathVariable(value = "id") long id, @RequestBody String json, HttpServletResponse response) throws IOException {
        System.out.println("calling /user/{ID}/edit now");
        User editUser = userRepository.findById(id);
        JSONObject object = new JSONObject();
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);


        editUser.setEmailVerified(obj.get("email_verified").getAsBoolean());
        editUser.setAdmin(obj.get("admin").getAsBoolean());
        UserPrincipal.create(editUser);
        userRepository.save(editUser);
        response.sendError(200, "Access granted to user");
    }
}

