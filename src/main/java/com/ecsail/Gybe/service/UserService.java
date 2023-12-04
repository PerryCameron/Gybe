package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.RoleDTO;
import com.ecsail.Gybe.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        if(!username.equals("Perry")) throw new UsernameNotFoundException("Not Perry");

        Set<RoleDTO> roles = new HashSet<>();
        roles.add(new RoleDTO(1,"USER"));

        return new UserDTO(1, "Perry", encoder.encode("password"), 0, roles);
    }
}
