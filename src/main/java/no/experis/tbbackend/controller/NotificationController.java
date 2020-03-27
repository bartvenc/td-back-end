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

/**
 * The type Notification controller.
 */
@RestController
public class NotificationController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Gets notification.
     *
     * @param userPrincipal the user principal
     * @param response      the response
     * @return the notification
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/notification/")
    public List<Notification> getNotification(@CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();

        List<Notification> theList = Singleton.getInstance().getArrayList();

        List<Notification> returList = theList.stream()
                .filter(p -> p.getUser_id() == id && p.isAdmin()).collect(Collectors.toList());

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

    /**
     * Gets notification as admin.
     *
     * @param userPrincipal the user principal
     * @param response      the response
     * @return the notification as admin
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/notification/")
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
