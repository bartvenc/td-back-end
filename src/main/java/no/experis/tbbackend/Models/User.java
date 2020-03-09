package no.experis.tbbackend.Models;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    private Byte[] profileImage;
}
