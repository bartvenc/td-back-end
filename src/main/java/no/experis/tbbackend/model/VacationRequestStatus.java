package no.experis.tbbackend.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;


/**
 * The type Vacation request status.
 */
@Entity
@Table(name = "vacation_request_status")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "status_id")
public class VacationRequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int status_id;
    private String status;

    /**
     * Gets status id.
     *
     * @return the status id
     */
    public int getStatus_id() {
        return status_id;
    }

    /**
     * Sets status id.
     *
     * @param status_id the status id
     */
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
