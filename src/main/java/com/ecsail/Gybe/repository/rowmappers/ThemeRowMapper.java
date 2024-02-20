package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.ThemeDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeRowMapper implements RowMapper<ThemeDTO> {

//    p.p_id,p.ms_id,p.member_type,p.f_name,p.l_name,e.email,p.nick_name
    @Override
    public ThemeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ThemeDTO(
                rs.getInt("STICKER_ID"),
                rs.getString("sticker_name"),
                rs.getString("url"),
                rs.getString("year_color")
        );
    }
}
