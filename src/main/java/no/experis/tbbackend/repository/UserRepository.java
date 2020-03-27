package no.experis.tbbackend.repository;


import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.IneligiblePeriod;
import no.experis.tbbackend.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<User> findByName(String name);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    Boolean existsByEmail(String email);

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    User findById(long id);

    @Override
    public List<User> findAll();

}
