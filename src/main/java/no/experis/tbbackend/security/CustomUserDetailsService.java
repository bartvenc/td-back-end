package no.experis.tbbackend.security;


import no.experis.tbbackend.exception.ResourceNotFoundException;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Custom user details service. Fetches the User details used for authentication,
 * and validation based on roles.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    /**
     * Load user by id user details.
     *
     * @param id the id
     * @return the user details object
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}