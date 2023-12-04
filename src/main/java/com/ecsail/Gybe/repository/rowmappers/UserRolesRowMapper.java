package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.UserRolesDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRolesRowMapper implements RowMapper<UserRolesDTO> {
    @Override
    public UserRolesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserRolesDTO(
                rs.getInt("user_id"),
                rs.getString("role_id"));
    }
}
