package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.UserDTO;

public interface AuthenticationService {
    UserDTO registerUser(String username, String password);

    String updatePassword(String password);
}
