package no.experis.tbbackend.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


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

//    @ManyToMany(cascade = CascadeType.ALL)

    private int request_id;

    private int user_id;


}
