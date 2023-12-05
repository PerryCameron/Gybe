package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.FormHashRequestDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormHashRequestRowMapper implements RowMapper<FormHashRequestDTO> {
    @Override
    public FormHashRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FormHashRequestDTO(
        rs.getInt("FORM_HASH_ID"),
        rs.getString("REQ_DATE"),
        rs.getString("PRI_MEM"),
        rs.getString("LINK"),
        rs.getInt("MSID"),
        rs.getString("MAILED_TO"));
    }
}
