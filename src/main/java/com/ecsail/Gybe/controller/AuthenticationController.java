package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.LoginResponseDTO;
import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin("*")
public class AuthenticationController {

//    private final AuthenticationService authenticationService;

//    @Autowired
//    public AuthenticationController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

//    @PostMapping("/register")
//    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
//        return authenticationService.registerUser(userDTO.getUsername(), userDTO.getPassword());
//    }

//    @PostMapping("/login")
//    public LoginResponseDTO loginUser(@RequestBody UserDTO body) {
//        return authenticationService.loginUser(body.getUsername(), body.getPassword());
//    }


}


