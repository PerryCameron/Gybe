package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.AuthDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRowMapper implements RowMapper<AuthDTO> {

//    p.p_id,p.ms_id,p.member_type,p.f_name,p.l_name,e.email,p.nick_name
    @Override
    public AuthDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AuthDTO(
                rs.getInt("P_ID"),
                rs.getInt("MS_ID"),
                rs.getInt("MEMBER_TYPE"),
                rs.getString("F_NAME"),
                rs.getString("L_NAME"),
                rs.getString("EMAIL"),
                rs.getString("NICK_NAME")
        );
    }
}
