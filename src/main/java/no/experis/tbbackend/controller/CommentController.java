package no.experis.tbbackend.controller;

import no.experis.tbbackend.model.Comment;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import no.experis.tbbackend.repository.CommentRepo;
import no.experis.tbbackend.repository.UserRepository;
import no.experis.tbbackend.repository.VacationRequestRepo;
import no.experis.tbbackend.security.CurrentUser;
import no.experis.tbbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CommentController {
    @Autowired
    private UserRepository userRepository;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("admin/request/{r_id}/comment")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Comment> getAllCommentsFromRequestAsAdmin(@PathVariable int r_id,
                                                          HttpServletResponse response) throws IOException {
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        CommentRepo commentRepo = new CommentRepo();
        List<Comment> returnComments = null;
        VacationRequest editVacation = vacationRequestRepo.findById(r_id);
        if (editVacation != null) {
            returnComments = new ArrayList<>(editVacation.getComment());

            Collections.sort(returnComments, new Comparator<Comment>() {
                public int compare(Comment o1, Comment o2) {
                    if (o1.getDate() == null || o2.getDate() == null)
                        return 0;
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            response.setStatus(200);
        } else {
            response.sendError(400, "VR not found");
        }
        return returnComments;

    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/request/{r_id}/comment")
    public List<Comment> getAllCommentsFromRequest(@PathVariable int r_id,
                                                   @CurrentUser UserPrincipal userPrincipal,
                                                   HttpServletResponse response) throws IOException {

        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        CommentRepo commentRepo = new CommentRepo();

        VacationRequest editVacation = vacationRequestRepo.findById(r_id);


        if ((requestUser.getId() == editVacation.getOwner().iterator().next().getId()) && editVacation != null) {
            List<Comment> returnComments = new ArrayList<>(editVacation.getComment());

            Collections.sort(returnComments, new Comparator<Comment>() {
                public int compare(Comment o1, Comment o2) {
                    if (o1.getDate() == null || o2.getDate() == null)
                        return 0;
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            return returnComments;
        } else {
            response.sendError(403, "Forbidden");
            return null;
        }

    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/request/{r_id}/comment")
    public Comment addCommentToVacationRequest(@PathVariable int r_id, @RequestBody Comment comment,
                                               @CurrentUser UserPrincipal userPrincipal,
                                               HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        CommentRepo commentRepo = new CommentRepo();

        VacationRequest editVacation = vacationRequestRepo.findById(r_id);
        //Comment newComment = new Comment(comment.getMessage(), comment.getDatetimestamp());

        if ((requestUser.getId() == editVacation.getOwner().iterator().next().getId())) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            comment.setDatetimestamp(sdf.format(timestamp).toString());
            comment.setDate(new Date(System.currentTimeMillis()));
            commentRepo.save(comment);

            editVacation.addComment(comment);
            System.out.println("new comment " + editVacation.getComment().iterator().next().getMessage());

            vacationRequestRepo.update(editVacation);
            //Comment newnewComment = commentRepo.findById(comment.getComment_id());
            comment.addUser(requestUser);
            commentRepo.update(comment);
            response.setStatus(200);
            return comment;
        } else {
            response.sendError(403);
            return null;
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("admin/request/{r_id}/comment")
    @PreAuthorize("hasRole('ADMIN')")
    public Comment addCommentToVacationRequestAsAdmin(@PathVariable int r_id, @RequestBody Comment comment,
                                                      @CurrentUser UserPrincipal userPrincipal,
                                                      HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        CommentRepo commentRepo = new CommentRepo();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        comment.setDatetimestamp(sdf.format(timestamp).toString());
        comment.setDate(new Date(System.currentTimeMillis()));

        VacationRequest editVacation = vacationRequestRepo.findById(r_id);
        if (editVacation != null) {
            commentRepo.save(comment);
            editVacation.addComment(comment);
            vacationRequestRepo.update(editVacation);

            comment.addUser(requestUser);
            commentRepo.update(comment);
            response.setStatus(200);
        } else {
            response.sendError(400, "Coud not find Vacation");
        }

        return comment;
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("admin/request/{r_id}/comment/{c_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Comment getRequestCommentByIDAsAdmin(@PathVariable int r_id, @PathVariable int c_id,
                                                @CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {

        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        CommentRepo commentRepo = new CommentRepo();

        Comment comment = commentRepo.findById(c_id);
        if (comment != null) {
            response.setStatus(200);
        } else {
            response.sendError(400, "Could not find comment");
        }

        return comment;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/request/{r_id}/comment/{c_id}")
    public Comment getRequestCommentByID(@PathVariable int r_id, @PathVariable int c_id,
                                         @CurrentUser UserPrincipal userPrincipal, HttpServletResponse response) throws IOException {
        long id = userPrincipal.getId();
        User requestUser = userRepository.findById(id);
        VacationRequestRepo vacationRequestRepo = new VacationRequestRepo();
        VacationRequest vacationRequest = vacationRequestRepo.findById(r_id);

        CommentRepo commentRepo = new CommentRepo();


        if ((requestUser.getId() != vacationRequest.getOwner().iterator().next().getId())) {
            Comment comment = commentRepo.findById(c_id);
            response.setStatus(200);
            return comment;
        } else {
            response.sendError(403, "Forbidden");
            return null;
        }
    }
}
