package no.experis.tbbackend.controller;

import no.experis.tbbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationController {
    @Autowired
    private UserRepository userRepository;
/*
    @CrossOrigin(origins="*", allowedHeaders="*")
    @GetMapping("/notification/{id}")
    public Notification getNotification(@CurrentUser UserPrincipal userPrincipal, HttpServletResponse response){
        long id = userPrincipal.getId();
        User notificationUser = userRepository.findById(id);

        NotificationRepo notificationRepo = new NotificationRepo();

    }
*/
}
