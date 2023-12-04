package com.ecsail.Gybe.dto;

import org.springframework.security.core.GrantedAuthority;

public class RoleDTO implements GrantedAuthority {

    int roleId;
    String authority;

    public RoleDTO() {
        super();
    }

    public RoleDTO(String authority) {
        this.authority = authority;
    }

    public RoleDTO(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
