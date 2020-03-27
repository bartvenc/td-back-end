package no.experis.tbbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * The type User.
 */
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

    /**
     * Instantiates a new User.
     */
    public User() {
        this.name = "null";
        this.admin = false;
        this.email = "null";
        this.image_Url = "null";
        this.emailVerified = false;
    }

    /**
     * Instantiates a new User.
     *
     * @param name          the name
     * @param admin         is admin
     * @param image_Url     the image url
     * @param emailVerified the email verified
     */
    public User(String name, boolean admin, String image_Url, boolean emailVerified) {
        this.name = name;
        this.admin = admin;
        this.image_Url = image_Url;
        this.emailVerified = emailVerified;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return image_Url;
    }

    /**
     * Sets image url.
     *
     * @param image_Url the image url
     */
    public void setImageUrl(String image_Url) {
        this.image_Url = image_Url;
    }

    /**
     * Gets email verified.
     *
     * @return the email verified
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     * Sets email verified.
     *
     * @param emailVerified the email verified
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets provider.
     *
     * @return the provider
     */
    public AuthProvider getProvider() {
        return provider;
    }

    /**
     * Sets provider.
     *
     * @param provider the provider
     */
    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    /**
     * Gets provider id.
     *
     * @return the provider id
     */
    public String getProviderId() {
        return provider_Id;
    }

    /**
     * Sets provider id.
     *
     * @param provider_Id the provider id
     */
    public void setProviderId(String provider_Id) {
        this.provider_Id = provider_Id;
    }

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets admin.
     *
     * @param admin the admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
