package com.ecsail.Gybe.models;

//import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {


    private String username;
    private String password;
    private boolean active;
//    private int pId;
//    private List<GrantedAuthority> authorities = new ArrayList<>();


    public UserDTO(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
//        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



//    public List<GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(List<GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }
}