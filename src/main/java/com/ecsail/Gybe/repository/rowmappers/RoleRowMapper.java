package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.RoleDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<RoleDTO> {
    @Override
    public RoleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoleDTO(
                rs.getInt("role_id"),
                rs.getString("role_name"));
    }
}
