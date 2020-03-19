package no.experis.tbbackend.controller;


import com.fasterxml.jackson.databind.node.TextNode;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class VacationRequestController {


    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins="*", allowedHeaders="*")
    @GetMapping("/request")
    public Set<VacationRequest> getUsersVacationRequest(@CurrentUser UserPrincipal userPrincipal,  HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        User requestUser = userRepository.findById(id);
        List<VacationRequest> vacationRequests;

        vacationRequests = vacationRequestRepo.findAllByUserID(requestUser.getId().intValue());
        Set<VacationRequest> uniqueSet = new HashSet<>(vacationRequests);

        List<VacationRequest> approvedVacationRequests = vacationRequestRepo.findAllAproved();
        uniqueSet.addAll(approvedVacationRequests);

        if(uniqueSet.isEmpty()){
            response.sendError(400,"Vacation request not found");
            return null;
        }

        response.setStatus(200);
        return uniqueSet;
    }


    @CrossOrigin(origins="*", allowedHeaders="*")
    @PostMapping("/request")
    public int createRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody VacationRequest vacationRequest) {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();

        vacationRequestRepo.save(vacationRequest);

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

    @CrossOrigin(origins="*", allowedHeaders="*")
    @PatchMapping("/admin/request/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editRequest(@PathVariable int id, @RequestBody TextNode status){
        System.out.println("EDIT STATUS");
        System.out.println("STATUS "+ status);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequestStatusRepo vacationRequestStatusRepo = new VacationRequestStatusRepo();
        VacationRequest editVacation = vacationRequestRepo.findById(id);
        VacationRequestStatus requestStatus = vacationRequestStatusRepo.findById(editVacation.getStatus().iterator().next().getStatus_id());
        System.out.println("REQUESTSTATUS WITH ID"+requestStatus.getStatus_id()+" "+requestStatus.getStatus());
        String gotStatus = status.asText();
        System.out.println("SETTING THIS VALUE TO STATUS "+gotStatus);
        requestStatus.setStatus(gotStatus);
        System.out.println("EDITEDREQUESTSTATUS "+requestStatus.getStatus());
        vacationRequestStatusRepo.update(requestStatus);
        vacationRequestRepo.update(editVacation);

        return "Status changed to "+status;
    }


}
