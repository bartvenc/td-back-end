package no.experis.tbbackend.Models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;



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

    private int owner_id;

    private int state_id;

    private int moderator_id;
}
