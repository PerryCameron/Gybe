package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormRequestSummaryRowMapper implements RowMapper<FormRequestSummaryDTO> {

    @Override
    public FormRequestSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FormRequestSummaryDTO(
        rs.getTimestamp("newest_hash_req_date"),
        rs.getString("PRI_MEM"),
        rs.getString("LINK"),
        rs.getString("MAILED_TO"),
        rs.getInt("num_hash_duplicates"),
        rs.getTimestamp("newest_form_req_date"),
        rs.getInt("num_form_attempts"));
    }
}
