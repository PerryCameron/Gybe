package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.RoleDTO;
import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {


    private AuthenticationRepository authenticationRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationService(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        RoleDTO userRole = authenticationRepository.findByAuthority("USER").get();
        UserDTO userDTO = authenticationRepository.saveUser(new UserDTO(0, username, encodedPassword, 0));
        authenticationRepository.saveUserRole(userDTO, userRole);
        return null;
    }



}


