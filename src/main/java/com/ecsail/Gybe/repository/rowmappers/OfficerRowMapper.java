package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.OfficerDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficerRowMapper implements RowMapper<OfficerDTO> {
    @Override
    public OfficerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OfficerDTO(
                rs.getInt("O_ID"),
                rs.getInt("P_ID"),
                rs.getInt("BOARD_YEAR"), // beginning of board term
                rs.getString("OFF_TYPE"),
                rs.getInt("OFF_YEAR"));
    }
}
