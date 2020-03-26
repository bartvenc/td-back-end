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

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Boolean existsByEmail(String email);

    User findById(long id);

    @Override
    public List<User> findAll();

}
