package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.UserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDTO> {
    @Override
    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDTO(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("p_id"));
    }
}
