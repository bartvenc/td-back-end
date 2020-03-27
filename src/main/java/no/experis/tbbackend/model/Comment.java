package no.experis.tbbackend.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Comment.
 */
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

    private String datetimestamp;
    @JsonIgnore

    private Date date;


    /**
     * Gets datetimestamp.
     *
     * @return the datetimestamp
     */
    public String getDatetimestamp() {
        return datetimestamp;
    }

    /**
     * Sets datetimestamp.
     *
     * @param datetimestamp the datetimestamp
     */
    public void setDatetimestamp(String datetimestamp) {
        this.datetimestamp = datetimestamp;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_user",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> user;

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }


    /**
     * Instantiates a new Comment.
     */
    public Comment() {

        this.message = "null";
        this.datetimestamp = "null";
        this.date = null;
        this.user = new HashSet<>();
    }

    /**
     * Instantiates a new Comment.
     *
     * @param message the message
     */
    public Comment(String message) {
        this.message = message;
        this.user = new HashSet<>();
    }

    /**
     * Gets comment id.
     *
     * @return the comment id
     */
    public int getComment_id() {
        return comment_id;
    }

    /**
     * Sets comment id.
     *
     * @param comment_id the comment id
     */
    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets user set.
     *
     * @return the user
     */
    public Set<User> getUser() {
        return user;
    }

    /**
     * Sets user set.
     *
     * @param user the user
     */
    public void setUser(Set<User> user) {
        this.user = user;
    }

    /**
     * Add user to user Set.
     *
     * @param user the user
     */
    public void addUser(User user) {
        this.user.add(user);
    }
}

