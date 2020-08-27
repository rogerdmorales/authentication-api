package br.com.roger.authenticationapi.service;

import br.com.roger.authenticationapi.domain.User;
import br.com.roger.authenticationapi.enums.APIErrors;
import br.com.roger.authenticationapi.exception.APIException;
import br.com.roger.authenticationapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser( User user) {

        if (userRepository.existsByEmail( user.getEmail() )) {
            throw new APIException( APIErrors.EMAIL_ALREADY_EXISTS );
        }

        user.setPassword( passwordEncoder.encode( user.getPassword() ) );

        return userRepository.save( user );
    }
}
