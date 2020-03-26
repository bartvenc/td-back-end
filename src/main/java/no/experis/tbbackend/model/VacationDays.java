package no.experis.tbbackend.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "vacation_days")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "vacation_day_id")
public class VacationDays {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vacation_day_id;

}
