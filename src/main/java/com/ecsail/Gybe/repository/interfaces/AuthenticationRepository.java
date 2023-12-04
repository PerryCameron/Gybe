package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.UserDTO;

import java.util.Optional;

public interface AuthenticationRepository {
    Optional<UserDTO> findByUsername(String username);

}
