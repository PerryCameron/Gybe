package com.ecsail.Gybe.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


public class UserDTO implements UserDetails {

    private int userId;
    private String username;
    private String password;
    private int pId;
    private Set<RoleDTO> authorities;

    public UserDTO() {
        super();
        this.authorities = new HashSet<RoleDTO>();
    }

    public UserDTO(Integer userId, String username, String password, Integer pId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.pId = pId;
    }

    public UserDTO(Integer userId, String username, String password, Integer pId, Set<RoleDTO> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.pId = pId;
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<RoleDTO> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}