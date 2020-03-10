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
            name = "request_state",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Set<VacationRequestStatus> status;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriod_start() {
        return period_start;
    }

    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    public String getPeriod_end() {
        return period_end;
    }

    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    public Set<User> getOwner() {
        return owner;
    }

    public void setOwner(Set<User> owner) {
        this.owner = owner;
    }

    public Set<VacationRequestStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<VacationRequestStatus> status) {
        this.status = status;
    }

    public Set<User> getModerator_id() {
        return moderator_id;
    }

    public void setModerator_id(Set<User> moderator_id) {
        this.moderator_id = moderator_id;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "request_moderator",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> moderator_id;
}
