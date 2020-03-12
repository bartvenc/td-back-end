package no.experis.tbbackend.Controllers;

import no.experis.tbbackend.Models.User;
import no.experis.tbbackend.Models.VacationRequest;
import no.experis.tbbackend.Repositories.UserRepo;
import no.experis.tbbackend.Repositories.VacationRequestRepo;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
public class UserControllers {
    UserRepo userRepo = new UserRepo();


    //TODO need to check for admin or not and fix data.
    @PreAuthorize("hasRole('USER')")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{ID}")
    public String getUser(@PathVariable Integer ID, HttpServletResponse response) throws IOException {
        System.out.println("Trying to find user: " + ID);
        User returnUser = null;

        returnUser = userRepo.findById(ID);
        if (returnUser == null) {
            response.setStatus(400);
            System.out.println(" --- user WAS NOT FOUND --- ");
        } else {

            String jsonString = new JSONObject().put("id", returnUser.getId()).
                    put("name", returnUser.getName()).
                    put("profileImage", returnUser.getProfileImage()).
                    toString();
            response.setStatus(200);
            return jsonString;
        }
        response.sendError(400, "User not found");
        return "User not found";
    }

    //             else if (false) {
//                String jsonString = new JSONObject().put("id", returnUser.getId()).
//                        put("name", returnUser.getName()).
//                        put("profileImage", returnUser.getProfileImage()).
//                        put("email", returnUser.getEmail()).
//                        toString();
//                response.setStatus(200);
//                return jsonString;
//            }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{ID}/requests")
    public Set<VacationRequest> getUserRequests(@PathVariable Integer ID, HttpServletResponse response) throws IOException {
        System.out.println("Trying to find user: " + ID);
        User returnUser = null;

        returnUser = userRepo.findById(ID);
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();

        if (returnUser == null) {
            response.setStatus(400);
            System.out.println(" --- user WAS NOT FOUND --- ");
        } else {
            Set<VacationRequest> userRequests = (Set<VacationRequest>) vacationRequestRepo.findAllByUserId(returnUser.getId());
            if (true) {

                response.setStatus(200);
                return userRequests;
            } else if (false) {

                return null;
            }
        }
        response.sendError(400, "User not found");
        return null;
    }


}
