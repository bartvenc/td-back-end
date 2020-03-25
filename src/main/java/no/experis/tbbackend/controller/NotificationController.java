package no.experis.tbbackend.controller;

import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.notification.Notification;
import no.experis.tbbackend.notification.Singleton;
import no.experis.tbbackend.notification.VacationRequestNotification;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import no.experis.tbbackend.controller.NotificationController.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NotificationController {
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/notification/")
    public List<Notification> getNotification(@CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();

        List<Notification> theList = Singleton.getInstance().getArrayList();
        System.out.println("The whole list");
        for (Notification n : theList) {
            System.out.println(n.getType());
        }
        List<Notification> returList = theList.stream()
                .filter(p -> p.getUser_id() == id && p.isAdmin()).collect(Collectors.toList());


        System.out.println("The filtered list for user " + id);
        for (Notification n : returList) {
            System.out.println(n.getType() + " " + n.getUser_id());
        }
        if (!theList.isEmpty()) {
            response.setStatus(200);
        } else {
            response.sendError(400, "Not notifications found for the user");
        }
        return returList;
    }


    @GetMapping("admin/notification/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Notification> getNotificationAsAdmin(@CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();

        List<Notification> theList = Singleton.getInstance().getArrayList();

        List<Notification> returList = theList.stream()
                .filter(p -> !p.isAdmin()).collect(Collectors.toList());

        for (Notification n : returList) {
            System.out.println(n.getType() + " " + n.getUser_id());
        }
        if (!theList.isEmpty()) {
            response.setStatus(200);
        } else {
            response.sendError(400, "Not notifications found for the user");
        }
        return returList;
    }

}
