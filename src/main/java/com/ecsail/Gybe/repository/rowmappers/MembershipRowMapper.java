package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.MembershipDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipRowMapper implements RowMapper<MembershipDTO> {

    @Override
    public MembershipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MembershipDTO membership = new MembershipDTO();
        membership.setMs_id(rs.getInt("ms_id"));
        membership.setP_id(rs.getInt("p_id"));
        membership.setJoin_date(rs.getString("join_date"));
        membership.setMem_type(rs.getString("mem_type"));
        membership.setAddress(rs.getString("address"));
        membership.setCity(rs.getString("city"));
        membership.setState(rs.getString("state"));
        membership.setZip(rs.getString("zip"));
        return membership;
    }


}