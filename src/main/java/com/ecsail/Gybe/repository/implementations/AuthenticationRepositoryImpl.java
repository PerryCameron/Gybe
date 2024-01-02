package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.RoleDTO;
import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import com.ecsail.Gybe.repository.rowmappers.RoleRowMapper;
import com.ecsail.Gybe.repository.rowmappers.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationRepository.class);

    @Autowired
    public AuthenticationRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        String query = "SELECT * from users where username=?";
        try {
            UserDTO user = template.queryForObject(query, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Set<RoleDTO> getAuthoritiesById(Integer userId) {
        String query = "SELECT r.role_id, r.role_name FROM roles r " +
                "INNER JOIN user_roles ur ON r.role_id = ur.role_id " +
                "WHERE ur.user_id = ?";
        return new HashSet<>(template.query(query, new RoleRowMapper(), userId));
    }

    // UserDTO has the field "private Set<RoleDTO> authorities", I would like to combine the above two methods
    //    so that I end up with a fully populated UserDTO, only provide the method

    @Override
    public Optional<RoleDTO> findByAuthority(String authority) {
        String query = "SELECT * FROM roles WHERE role_name=?";
        try {
            RoleDTO role = template.queryForObject(query, new RoleRowMapper(), authority);
            return Optional.ofNullable(role);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public RoleDTO saveAuthority(String authority) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = "INSERT INTO roles (role_name) VALUES (?)";
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"role_id"});
            ps.setString(1, authority);
            return ps;
        }, keyHolder);
        return new RoleDTO(keyHolder.getKey().intValue(), authority);
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        String insertQuery = "INSERT INTO users (username, password, p_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"user_id"});
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setInt(3, user.getpId());
                return ps;
            }, keyHolder);
            user.setUserId(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return user;
    }


    @Override
    public Void saveUserRole(UserDTO user, RoleDTO role) {
        String insertQuery = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        try {
            template.update(insertQuery, user.getUserId(), role.getRoleId());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<UserDTO> findUserWithAuthoritiesByUsername(String username) {
        String userQuery = "SELECT * FROM users WHERE username=?";
        String rolesQuery = "SELECT r.role_id, r.role_name FROM roles r " +
                "INNER JOIN user_roles ur ON r.role_id = ur.role_id " +
                "WHERE ur.user_id = ?";
        try {
            UserDTO user = template.queryForObject(userQuery, new UserRowMapper(), username);
            if (user != null) {
                Set<RoleDTO> authorities = new HashSet<>(template.query(rolesQuery, new RoleRowMapper(), user.getUserId()));
                user.setAuthorities(authorities);
            }
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }




}
