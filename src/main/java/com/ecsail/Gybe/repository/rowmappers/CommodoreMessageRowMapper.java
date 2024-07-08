package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommodoreMessageRowMapper implements RowMapper<CommodoreMessageDTO> {
    @Override
    public CommodoreMessageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommodoreMessageDTO(
                rs.getInt("id"),
                rs.getInt("fiscal_year"),
                rs.getString("salutation"),
                rs.getString("message"),
                rs.getString("commodore"),
                rs.getInt("pid"));
    }
}
