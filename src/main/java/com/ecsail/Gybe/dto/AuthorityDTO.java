package com.ecsail.Gybe.dto;

//import org.springframework.security.core.GrantedAuthority;
public class AuthorityDTO {
//public class AuthorityDTO implements GrantedAuthority {
    int id;
    String userName;
    String authority;

    public AuthorityDTO(int id, String userName, String authority) {
        this.id = id;
        this.userName = userName;
        this.authority = authority;
    }

    public AuthorityDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
