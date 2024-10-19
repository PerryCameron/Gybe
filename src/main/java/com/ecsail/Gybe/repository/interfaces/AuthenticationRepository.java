package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.RoleDTO;
import com.ecsail.Gybe.dto.UserDTO;

import java.util.Optional;
import java.util.Set;

public interface AuthenticationRepository {
    Optional<UserDTO> findByUsername(String username);
    Optional<RoleDTO> findByAuthority(String authority);
    RoleDTO saveAuthority(String authority);

    UserDTO saveUser(UserDTO user);
    Void saveUserRole(UserDTO user, RoleDTO role);

    boolean existsByUsername(String username);

    boolean apiKeyIsGood(String keyType, String apiKey);

    Set<RoleDTO> getAuthoritiesById(Integer userId);

    Optional<UserDTO> findUserWithAuthoritiesByUsername(String username);

    int updatePassword(String encryptedPassword, int pid);

    int recordLoginEvent(String username, boolean status);
}
