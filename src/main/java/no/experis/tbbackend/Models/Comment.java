package no.experis.tbbackend.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Comments")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "comment_id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int comment_id;
    private String message;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "comment_user",
            joinColumns = @JoinColumn(name= "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "vacationRequest_comments",
            joinColumns = @JoinColumn(name= "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id")
    )
    private Set<VacationRequest> vacationRequests;



}
