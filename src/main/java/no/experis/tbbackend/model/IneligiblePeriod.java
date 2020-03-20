package no.experis.tbbackend.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    public IneligiblePeriod(String period_start, String period_end) {
        this.period_start = period_start;
        this.period_end = period_end;
        this.created_by = new HashSet<>();
    }

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

    public int getIp_id() {
        return ip_id;
    }

    public void setIp_id(int ip_id) {
        this.ip_id = ip_id;
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

    public Set<User> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Set<User> created_by) {
        this.created_by = created_by;
    }

    public void addUser(User user) {
        this.created_by.add(user);
    }
}

