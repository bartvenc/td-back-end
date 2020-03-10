package no.experis.tbbackend.Models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "VacationRequests")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "request_id")


public class VacationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int request_id;
    private String title;
    private String period_start;
    private String period_end;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "request_user",
            joinColumns = @JoinColumn(name= "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "request_state",
            joinColumns = @JoinColumn(name= "request_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Set<VacationRequestStatus> status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "request_moderator",
            joinColumns = @JoinColumn(name= "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> moderator_id;
}
