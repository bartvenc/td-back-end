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
import java.io.IOException;
import java.util.List;

/**
 * The type Ineligible period controller.
 */
@RestController
public class IneligiblePeriodController {

    @Autowired
    private UserRepository userRepository;


    /**
     * Create Ineligible Period .
     *
     * @param userPrincipal    the user principal
     * @param ineligiblePeriod the ineligible period
     * @param response         the response
     * @return the ineligible period id
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/admin/ineligible")
    @PreAuthorize("hasRole('ADMIN')")
    public int createIP(@CurrentUser UserPrincipal userPrincipal, @RequestBody IneligiblePeriod ineligiblePeriod, HttpServletResponse response) {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        IneligiblePeriodRepo ineligiblePeriodRepo = new IneligiblePeriodRepo();
        ineligiblePeriodRepo.save(ineligiblePeriod);
        ineligiblePeriod.addUser(requestUser);
        ineligiblePeriodRepo.update(ineligiblePeriod);
        response.setStatus(201);
        return ineligiblePeriod.getIp_id();
    }

    /**
     * Gets Ineligible Period.
     *
     * @param response the response
     * @return the Ineligible Period
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/admin/ineligible")
    public List<IneligiblePeriod> getIP(HttpServletResponse response) throws IOException {

        IneligiblePeriodRepo ineligiblePeriodRepo = new IneligiblePeriodRepo();
        List<IneligiblePeriod> IPs = ineligiblePeriodRepo.findAll();
        if (!IPs.isEmpty()) {
            response.setStatus(200);
        }
        return IPs;
    }

    /**
     * Gets ineligible period by id.
     *
     * @param ip_id    the ineligible period id
     * @param response the response
     * @return the ineligible period by id
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/admin/ineligible/{ip_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public IneligiblePeriod getIPById(@PathVariable int ip_id, HttpServletResponse response) throws IOException {
        IneligiblePeriodRepo ineligiblePeriodRepo = new IneligiblePeriodRepo();
        IneligiblePeriod returnIP = ineligiblePeriodRepo.findById(ip_id);
        if (returnIP != null) {
            response.setStatus(200);
        } else {
            response.sendError(400, "IP not found");
        }
        return returnIP;
    }

    /**
     * Delete ineligible period by id.
     *
     * @param ip_id    the ineligible period id
     * @param response the response
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "", allowedHeaders = "")
    @PatchMapping("/admin/ineligible/{ip_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteIPById(@PathVariable int ip_id, HttpServletResponse response) throws IOException {
        IneligiblePeriodRepo ineligiblePeriodRepo = new IneligiblePeriodRepo();
        IneligiblePeriod returnIP = ineligiblePeriodRepo.findById(ip_id);
        long userId = returnIP.getCreated_by().iterator().next().getId();

        ineligiblePeriodRepo.deleteInel(returnIP.getIp_id(), userId);
        response.setStatus(200);
    }
}
