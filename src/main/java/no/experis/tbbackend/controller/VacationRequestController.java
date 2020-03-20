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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class VacationRequestController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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


    @CrossOrigin(origins = "*", allowedHeaders = "*")
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
    //TODO add moderator id of admin accesings this endpoint
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/admin/request/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editRequest(@PathVariable int id, @RequestBody String status) {
        System.out.println("EDIT STATUS");
        System.out.println("STATUS " + status);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequestStatusRepo vacationRequestStatusRepo = new VacationRequestStatusRepo();

        VacationRequest editVacation = vacationRequestRepo.findById(id);
        VacationRequestStatus requestStatus = vacationRequestStatusRepo.findById(editVacation.getStatus().iterator().next().getStatus_id());

        System.out.println("REQUESTSTATUS WITH ID" + requestStatus.getStatus_id() + " " + requestStatus.getStatus());
        String gotStatus = status.toString();
        System.out.println("SETTING THIS VALUE TO STATUS " + gotStatus);
        requestStatus.setStatus(gotStatus);
        System.out.println("EDITEDREQUESTSTATUS " + requestStatus.getStatus());

        vacationRequestRepo.update(editVacation);
        System.out.println("From updated vacatio repo " + vacationRequestRepo.findById(editVacation.getRequest_id()).getStatus().iterator().next().getStatus());

        vacationRequestStatusRepo.update(requestStatus);
        System.out.println("updated request status from repo " + vacationRequestStatusRepo.findById(editVacation.getStatus().iterator().next().getStatus_id()).getStatus());


        return "Worked";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/request/{vr_ID}")
    public VacationRequest getVacationRequestByID(@PathVariable int vr_ID, @CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequestStatusRepo vacationRequestStatusRepo = new VacationRequestStatusRepo();

        User requestUser = userRepository.findById(id);

        VacationRequest returnVacation = vacationRequestRepo.findById(vr_ID);
        VacationRequestStatus requestStatus = vacationRequestStatusRepo.findById(returnVacation.getStatus().iterator().next().getStatus_id());

        if ((requestUser.getId() != returnVacation.getOwner().iterator().next().getId()) ||
                (!requestStatus.getStatus().equals("Approved"))) {
            response.sendError(403);
            return null;
        } else {
            response.setStatus(200);
            return returnVacation;
        }

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("admin/request/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public VacationRequest getVacationRequestByIdAsAdmin(@CurrentUser UserPrincipal userPrincipal, @PathVariable int id, HttpServletResponse response) throws IOException {
        System.out.println("inside /admin/request/{id}/");
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequest returnVacation = vacationRequestRepo.findById(id);
        if (returnVacation != null) {
            return returnVacation;
        } else {
            response.sendError(400, "vacation request not found");
            return null;
        }
    }


}
