package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.SlipDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SlipStructureRowMapper implements RowMapper<SlipStructureDTO> {

    @Override
    public SlipStructureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SlipStructureDTO(
                rs.getInt("id"),
                rs.getString("dock"),
                rs.getInt("dock_section"),
                rs.getString("slip1"),
                rs.getString("slip2"),
                rs.getString("slip3"),
                rs.getString("slip4"));
    }
}
