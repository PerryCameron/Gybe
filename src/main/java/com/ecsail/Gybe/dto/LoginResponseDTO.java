package com.ecsail.Gybe.dto;

import org.apache.catalina.User;

public class LoginResponseDTO {
    private UserDTO userDTO;
    private String jwt;

    public LoginResponseDTO(UserDTO userDTO, String jwt) {
        this.userDTO = userDTO;
        this.jwt = jwt;
    }

    public LoginResponseDTO() {
        super();
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
