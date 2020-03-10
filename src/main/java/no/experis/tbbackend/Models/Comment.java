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
            name = "vacationRequest_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id")
    )
    private Set<VacationRequest> vacationRequests;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Set<VacationRequest> getVacationRequests() {
        return vacationRequests;
    }

    public void setVacationRequests(Set<VacationRequest> vacationRequests) {
        this.vacationRequests = vacationRequests;
    }
}
