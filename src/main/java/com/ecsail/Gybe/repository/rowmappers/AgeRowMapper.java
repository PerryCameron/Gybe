package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.AgesDTO;
import com.ecsail.Gybe.dto.HashDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgeRowMapper implements RowMapper<AgesDTO> {
    @Override
    public AgesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AgesDTO(
                rs.getInt("0 - 10"),
                rs.getInt("11 - 20"),
                rs.getInt("21 - 30"),
                rs.getInt("31 - 40"),
                rs.getInt("41 - 50"),
                rs.getInt("51 - 60"),
                rs.getInt("61 - 70"),
                rs.getInt("71 - 80"),
                rs.getInt("81 - 90"),
                rs.getInt("Not Reported"),
                rs.getInt("Total People")
                );
    }
}
