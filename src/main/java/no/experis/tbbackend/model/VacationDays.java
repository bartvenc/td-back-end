package no.experis.tbbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "VacationDays")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "vacationDays_id")

public class VacationDays {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vacationDays_id;
    private int max_vacationDays;

    public VacationDays() {
        this.max_vacationDays = 0;
    }

    public VacationDays(int max_vacationDays) {
        this.max_vacationDays = max_vacationDays;
    }


    public int getVacationDays_id() {
        return vacationDays_id;
    }

    public void setVacationDays_id(int vacationDays_id) {
        this.vacationDays_id = vacationDays_id;
    }

    public int getMax_vacationDays() {
        return max_vacationDays;
    }

    public void setMax_vacationDays(int max_vacationDays) {
        this.max_vacationDays = max_vacationDays;
    }
}
