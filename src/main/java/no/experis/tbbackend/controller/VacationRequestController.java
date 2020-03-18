package no.experis.tbbackend.controller;


import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.model.VacationRequestStatus;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.repository.VacationRequestRepo;
import no.experis.tbbackend.repository.VacationRequestStatusRepo;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class VacationRequestController {


    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins="*", allowedHeaders="*")
    @GetMapping("/request")
    public Set<VacationRequest> getUsersVacationRequest(@CurrentUser UserPrincipal userPrincipal,  HttpServletResponse response){
        long id = userPrincipal.getId();
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        User requestUser = userRepository.findById(id);
        List<VacationRequest> vacationRequests;

        vacationRequests = vacationRequestRepo.findAllByUserID(requestUser.getId().intValue());
        Set<VacationRequest> uniqueSet = new HashSet<VacationRequest>(vacationRequests);
        List<VacationRequest> approvedVacationRequests = vacationRequestRepo.findAllAproved();
        uniqueSet.addAll(approvedVacationRequests);
        //vacationRequests.addAll(approvedVacationRequests);

        return uniqueSet;
    }


    @CrossOrigin(origins="*", allowedHeaders="*")
    @PostMapping("/request")
    public int createRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody VacationRequest vacationRequest) {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();

        vacationRequestRepo.save(vacationRequest);

        System.out.println("asdasdasdada " + vacationRequest.getRequest_id());
        vacationRequest.addOwner(requestUser);
        vacationRequestRepo.update(vacationRequest);

        VacationRequestStatus vacationRequestStatus = new VacationRequestStatus();
        vacationRequestStatus.setStatus("Pending");
        VacationRequestStatusRepo vacationRequestStatusRepo = new VacationRequestStatusRepo();
        vacationRequestStatusRepo.save(vacationRequestStatus);

        vacationRequest.addRequest(vacationRequestStatus);
        vacationRequestRepo.update(vacationRequest);

        if (vacationRequest.getRequest_id() > 0) {
            return vacationRequest.getRequest_id();
        } else {
            return -1;
        }
    }
}
