package br.com.roger.authenticationapi.service;

import br.com.roger.authenticationapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        return userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "Usuário inexistente" ));
    }
}
