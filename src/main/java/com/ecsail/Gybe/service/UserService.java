package com.ecsail.Gybe.service;

import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder encoder;

    private AuthenticationRepository authenticationRepository;
    @Autowired
    public UserService(PasswordEncoder encoder, AuthenticationRepository authenticationRepository) {
        this.encoder = encoder;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        return authenticationRepository.findUserWithAuthoritiesByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User is not valid"));
    }
}
