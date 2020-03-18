package no.experis.tbbackend.controller;


import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.model.VacationRequestStatus;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.repository.VacationRequestRepo;
import no.experis.tbbackend.repository.VacationRequestStatusRepo;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class VacationRequestController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/admin/request")
    @PreAuthorize("hasRole('ADMIN')")
    public List<VacationRequest> getAllVacationRequest(HttpServletResponse response) throws IOException {

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        List<VacationRequest> returnVacationRequests = vacationRequestRepo.findAll();
        if (returnVacationRequests.isEmpty()) {
            response.sendError(400, "No vacation request were found");
        } else {
            response.setStatus(200);
        }
        return returnVacationRequests;
    }

    @GetMapping("/request")
    public List<VacationRequest> getUsersVacationRequest(@CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) {
        long id = userPrincipal.getId();
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        User requestUser = userRepository.findById(id);
        List<VacationRequest> vacationRequests;

        vacationRequests = vacationRequestRepo.findAllByUserID(requestUser.getId().intValue());

        List<VacationRequest> appprovedVacationRequests = vacationRequestRepo.findAllAproved();

        vacationRequests.addAll(appprovedVacationRequests);

        HashSet<Object> seen = new HashSet<>();
        vacationRequests.removeIf(e -> !seen.add(e.getRequest_id()));
        return vacationRequests;
    }

    @PostMapping("/request")
    public int createRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody VacationRequest vacationRequest, HttpServletResponse response) {
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
