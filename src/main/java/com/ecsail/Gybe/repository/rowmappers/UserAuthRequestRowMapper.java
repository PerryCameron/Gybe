package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.UserAuthRequestDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthRequestRowMapper implements RowMapper<UserAuthRequestDTO> {
    @Override
    public UserAuthRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserAuthRequestDTO(
                rs.getString("pass_key"),
                rs.getInt("pid"),
                rs.getDate("updated_at").toLocalDate(), // beginning of board term
                rs.getDate("completed").toLocalDate());
    }
}
