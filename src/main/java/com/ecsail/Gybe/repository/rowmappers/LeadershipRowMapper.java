package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.LeadershipDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeadershipRowMapper implements RowMapper<LeadershipDTO> {
    @Override
    public LeadershipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new LeadershipDTO(
                rs.getInt("O_ID"),
                rs.getInt("P_ID"),
                rs.getString("F_Name"),
                rs.getString("L_Name"),
                rs.getString("position"), // beginning of board term
                rs.getBoolean("is_officer"),
                rs.getBoolean("is_chair"),
                rs.getBoolean("is_assistant_chair"));
    }
}
