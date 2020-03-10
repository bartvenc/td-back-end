package no.experis.tbbackend.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;



@Entity
@Table(name = "IneligiblePeriods")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ip_id")

public class IneligiblePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ip_id;
    private String period_start;
    private String period_end;

    private int created_by;


}
