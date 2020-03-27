package no.experis.tbbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * The type Vacation request.
 */
@Entity
@Table(name = "vacation_requests")
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

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public Set<Comment> getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    /**
     * Instantiates a new Vacation request.
     */
    public VacationRequest() {
        this.title = "null";
        this.period_start = "null";
        this.period_end = "null";
        this.owner = new HashSet<>();
        this.moderator_id = new HashSet<>();
        this.status = new HashSet<>();
        this.comment = new HashSet<>();
    }

    /**
     * Instantiates a new Vacation request.
     *
     * @param title        the title
     * @param period_start the period start
     * @param period_end   the period end
     */
    public VacationRequest(String title, String period_start, String period_end) {
        this.title = title;
        this.period_start = period_start;
        this.period_end = period_end;
        this.owner = new HashSet<User>();
        this.moderator_id = new HashSet<User>();
        this.status = new HashSet<VacationRequestStatus>();
        this.comment = new HashSet<>();
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "request_user",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> owner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "request_state",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Set<VacationRequestStatus> status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "vacation_request_comments",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> comment;

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public int getRequest_id() {
        return request_id;
    }

    /**
     * Sets request id.
     *
     * @param request_id the request id
     */
    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    /**
     * Add vacation request status.
     *
     * @param vacationRequestStatus the vacation request status
     */
    public void addRequest(VacationRequestStatus vacationRequestStatus) {
        this.status.add(vacationRequestStatus);
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets period start.
     *
     * @return the period start
     */
    public String getPeriod_start() {
        return period_start;
    }

    /**
     * Sets period start.
     *
     * @param period_start the period start
     */
    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    /**
     * Gets period end.
     *
     * @return the period end
     */
    public String getPeriod_end() {
        return period_end;
    }

    /**
     * Sets period end.
     *
     * @param period_end the period end
     */
    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    /**
     * Gets owner arrayList.
     *
     * @return the owner
     */
    public Set<User> getOwner() {
        return owner;
    }

    /**
     * Sets owner arrayList.
     *
     * @param owner the owner
     */
    public void setOwner(Set<User> owner) {
        this.owner = owner;
    }

    /**
     * Add user to owner array.
     *
     * @param owner the owner
     */
    public void addOwner(User owner) {
        this.owner.add(owner);
    }

    /**
     * Gets status array.
     *
     * @return the status
     */
    public Set<VacationRequestStatus> getStatus() {
        return status;
    }

    /**
     * Sets vacation request status into status array.
     *
     * @param status the status
     */
    public void setStatus(Set<VacationRequestStatus> status) {
        this.status = status;
    }

    /**
     * Gets moderator id array.
     *
     * @return the moderator id
     */
    public Set<User> getModerator_id() {
        return moderator_id;
    }

    /**
     * Sets moderator id array.
     *
     * @param moderator_id the moderator id
     */
    public void setModerator_id(Set<User> moderator_id) {
        this.moderator_id = moderator_id;
    }

    /**
     * Adds user to moderator array.
     *
     * @param moderator the moderator
     */
    public void addModerator(User moderator) {
        this.moderator_id.add(moderator);
    }

    /**
     * Add comment to comment array.
     *
     * @param comment the comment
     */
    public void addComment(Comment comment) {
        this.comment.add(comment);
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "request_moderator",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> moderator_id;
}

