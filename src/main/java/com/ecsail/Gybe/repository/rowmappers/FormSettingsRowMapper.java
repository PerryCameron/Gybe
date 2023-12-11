package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.dto.InvoiceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormSettingsRowMapper implements RowMapper<FormSettingsDTO> {

    @Override
    public FormSettingsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FormSettingsDTO(
                rs.getInt("PORT"),
                rs.getString("LINK"),
                rs.getString("form_url"),
                rs.getString("form_id"),
                rs.getInt("selected_year"));
    }
}
