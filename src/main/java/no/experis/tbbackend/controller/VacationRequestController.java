package no.experis.tbbackend.controller;

import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.model.VacationRequestStatus;
import no.experis.tbbackend.notification.Notification;
import no.experis.tbbackend.notification.Singleton;
import no.experis.tbbackend.notification.VacationRequestNotification;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Vacation request controller.
 */
@RestController
public class VacationRequestController {
    public List<Notification> notificationList = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");

    /**
     * Gets all vacation request.
     *
     * @param response the response
     * @return the all vacation request
     * @throws IOException the io exception
     */
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


    /**
     * Gets users vacation request.
     *
     * @param userPrincipal the user principal
     * @param response      the response
     * @return the users vacation request
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/request")
    public List<VacationRequest> getUsersVacationRequest(@CurrentUser UserPrincipal userPrincipal,
                                                         HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        User requestUser = userRepository.findById(id);
        List<VacationRequest> vacationRequests;

        vacationRequests = vacationRequestRepo.findAllByUserID(requestUser.getId().intValue());

        if (!vacationRequests.isEmpty()) {
            List<VacationRequest> appprovedVacationRequests = vacationRequestRepo.findAllAproved();

            vacationRequests.addAll(appprovedVacationRequests);

            HashSet<Object> seen = new HashSet<>();
            vacationRequests.removeIf(e -> !seen.add(e.getRequest_id()));
            response.setStatus(200);
        } else {
            response.sendError(400, "vacation not found");
        }

        return vacationRequests;
    }

    /**
     * Create request int.
     *
     * @param userPrincipal   the user principal
     * @param vacationRequest the vacation request
     * @param response        the response
     * @return the int
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/request")
    public int createRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody VacationRequest vacationRequest,
                             HttpServletResponse response) throws IOException {
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date newDate = new Date(System.currentTimeMillis());
        String dateStamp = sdf.format(timestamp).toString();

        VacationRequestNotification newNote = new VacationRequestNotification
                ("new VacationRequest", newDate,
                        dateStamp,
                        "Vacation Request " + vacationRequest.getTitle() +
                                " was created by " + vacationRequest.getOwner().iterator().next().getName(),
                        Long.toString(vacationRequest.getRequest_id()), id, false);

        Singleton.getInstance().getArrayList().add(newNote);


        if (vacationRequest.getRequest_id() > 0) {
            response.setStatus(201);
            return vacationRequest.getRequest_id();
        } else {
            response.sendError(400, "coud not create vacation");
            return -1;
        }
    }

    /**
     * Edit request string.
     *
     * @param id     the id
     * @param status the status
     * @return the string
     */
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

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date newDate = new Date(System.currentTimeMillis());
        String dateStamp = sdf.format(timestamp).toString();

        VacationRequestNotification newNote = new VacationRequestNotification
                ("new VacationRequest Status", newDate,
                        dateStamp,
                        "Vacation Request " + editVacation.getTitle() + " was " + gotStatus +
                                " by admin",
                        Long.toString(editVacation.getRequest_id()), editVacation.getOwner().iterator().next().getId(), true);

        Singleton.getInstance().getArrayList().add(newNote);

        return "Worked";
    }

    /**
     * Gets vacation request by id.
     *
     * @param vr_ID         the vr id
     * @param userPrincipal the user principal
     * @param response      the response
     * @return the vacation request by id
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/request/{vr_ID}")
    public VacationRequest getVacationRequestByID(@PathVariable int vr_ID, @CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequestStatusRepo vacationRequestStatusRepo = new VacationRequestStatusRepo();

        User requestUser = userRepository.findById(id);

        VacationRequest returnVacation = vacationRequestRepo.findById(vr_ID);
        VacationRequestStatus requestStatus = vacationRequestStatusRepo.findById(returnVacation.getStatus().iterator().next().getStatus_id());

        if ((!requestUser.getId().equals(returnVacation.getOwner().iterator().next().getId())) ||
                (!requestStatus.getStatus().equals("Approved"))) {
            response.sendError(403);
            return null;
        } else {
            response.setStatus(200);
            return returnVacation;
        }
    }

    /**
     * Gets vacation request by id as admin.
     *
     * @param userPrincipal the user principal
     * @param id            the id
     * @param response      the response
     * @return the vacation request by id as admin
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("admin/request/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public VacationRequest getVacationRequestByIdAsAdmin(@CurrentUser UserPrincipal userPrincipal, @PathVariable int id, HttpServletResponse response) throws IOException {
        System.out.println("/admin/request/{id}/");
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequest returnVacation = vacationRequestRepo.findById(id);
        if (returnVacation != null) {
            response.setStatus(200);
            return returnVacation;
        } else {
            response.sendError(400, "vacation request not found");
            return null;
        }
    }
}
