package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.RoleDTO;
import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.service.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {


    private AuthenticationRepository authenticationRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager
                                 ) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDTO registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        RoleDTO userRole = authenticationRepository.findByAuthority("ROLE_USER").get();
        UserDTO userDTO = authenticationRepository.saveUser(new UserDTO(0, username, encodedPassword, 0));
        authenticationRepository.saveUserRole(userDTO, userRole);
        return userDTO;
    }







}


