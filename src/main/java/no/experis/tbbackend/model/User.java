package no.experis.tbbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean admin = false;

    @Email
    @Column(nullable = false)
    private String email;

    private String image_Url;

    @Column(nullable = false, name = "email_verified")
    private Boolean emailVerified = false;


    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String provider_Id;

    public User() {
        this.name = "null";
        this.admin = false;
        this.email = "null";
        this.image_Url = "null";
        this.emailVerified = false;
    }

    public User(String name, boolean admin, String image_Url, boolean emailVerified) {
        this.name = name;
        this.admin = admin;
        this.image_Url = image_Url;
        this.emailVerified = emailVerified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return image_Url;
    }

    public void setImageUrl(String image_Url) {
        this.image_Url = image_Url;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return provider_Id;
    }

    public void setProviderId(String provider_Id) {
        this.provider_Id = provider_Id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
