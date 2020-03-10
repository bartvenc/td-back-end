package no.experis.tbbackend.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;



@Entity
@Table(name = "VacationRequestStatus")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "status_id")
public class VacationRequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int status_id;
    private String status;

}
