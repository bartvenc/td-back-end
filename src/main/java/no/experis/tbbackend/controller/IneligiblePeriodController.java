package no.experis.tbbackend.controller;

import no.experis.tbbackend.model.IneligiblePeriod;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.repository.IneligiblePeriodRepo;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IneligiblePeriodController {

    @Autowired
    private UserRepository userRepository;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/admin/ineligible")
    @PreAuthorize("hasRole('ADMIN')")
    public int createIP(@CurrentUser UserPrincipal userPrincipal, @RequestBody IneligiblePeriod ineligiblePeriod, HttpServletResponse response) {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        IneligiblePeriodRepo ineligiblePeriodRepo = new IneligiblePeriodRepo();
        //ineligiblePeriod.addUser(requestUser);
        ineligiblePeriodRepo.save(ineligiblePeriod);
        ineligiblePeriod.addUser(requestUser);
        ineligiblePeriodRepo.update(ineligiblePeriod);
        return ineligiblePeriod.getIp_id();
    }


}
