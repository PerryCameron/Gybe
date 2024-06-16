package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.UserDTO;

public interface AuthenticationService {
//    UserDTO registerUser(String username, String password);

    //    @Override
    //    public UserDTO registerUser(String username, String password) {
    //        String encodedPassword = passwordEncoder.encode(password);
    //        RoleDTO userRole = authenticationRepository.findByAuthority("ROLE_USER").get();
    //        UserDTO userDTO = authenticationRepository.saveUser(new UserDTO(0, username, encodedPassword, 0));
    //        authenticationRepository.saveUserRole(userDTO, userRole);
    //        return userDTO;
    //    }
    int recordLoginEvent(String username, boolean status);

    String updatePassword(String password);
}
