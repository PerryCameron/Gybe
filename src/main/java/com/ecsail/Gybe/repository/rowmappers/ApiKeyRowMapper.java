package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.ApiKeyDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiKeyRowMapper implements RowMapper<ApiKeyDTO> {
    @Override
    public ApiKeyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ApiKeyDTO(
                rs.getInt("API_ID"),
                rs.getString("NAME"),
                rs.getString("APIKEY")
                );
    }
}

