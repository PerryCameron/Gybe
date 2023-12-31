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
        System.out.println("registering the user");
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("encoded password:" + encodedPassword);
        RoleDTO userRole = authenticationRepository.findByAuthority("ROLE_USER").get();
        System.out.println("userRole=" + userRole);
        UserDTO userDTO = authenticationRepository.saveUser(new UserDTO(0, username, encodedPassword, 0));
        System.out.println("userDTO" + userDTO);
        authenticationRepository.saveUserRole(userDTO, userRole);
        return userDTO;
    }

//    public LoginResponseDTO loginUser(String username, String password) {
//        try {
//            // New authentication object, whenever we send in a request for a user login, it will pass
//            // the user, pass to this authentication manager, it will use the UserDetailsService that we set up earlier
//            // grab the user if user exists will create token for us to send, otherwise throw exception
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//            // send token to our JWT
//            String token = tokenService.generateJwt(auth);
//            // return back the user as well as the token
//            return new LoginResponseDTO(authenticationRepository.findUserWithAuthoritiesByUsername(username).get(),token);
//        } catch (Exception e) {
//            return  new LoginResponseDTO(null, "");
//        }
//
//    }



}


