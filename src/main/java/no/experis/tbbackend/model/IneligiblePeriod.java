package no.experis.tbbackend.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Ineligible period.
 */
@Entity
@Table(name = "ineligible_periods")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ip_id")

public class IneligiblePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ip_id;
    private String period_start;
    private String period_end;

    /**
     * Instantiates a new Ineligible period.
     *
     * @param period_start the period start
     * @param period_end   the period end
     */
    public IneligiblePeriod(String period_start, String period_end) {
        this.period_start = period_start;
        this.period_end = period_end;
        this.created_by = new HashSet<>();
    }

    /**
     * Instantiates a new Ineligible period.
     */
    public IneligiblePeriod() {
        this.period_end = "null";
        this.period_start = "null";
        this.created_by = new HashSet<>();
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ip_user",
            joinColumns = @JoinColumn(name = "ip_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> created_by;

    /**
     * Gets ip id.
     *
     * @return the ip id
     */
    public int getIp_id() {
        return ip_id;
    }

    /**
     * Sets ip id.
     *
     * @param ip_id the ip id
     */
    public void setIp_id(int ip_id) {
        this.ip_id = ip_id;
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
     * Gets created by.
     *
     * @return the created by
     */
    public Set<User> getCreated_by() {
        return created_by;
    }

    /**
     * Sets created by.
     *
     * @param created_by the created by
     */
    public void setCreated_by(Set<User> created_by) {
        this.created_by = created_by;
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
        this.created_by.add(user);
    }
}

