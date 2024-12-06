package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.SlipDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomSlipRowMapper implements RowMapper<SlipDTO> {

    @Override
    public SlipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SlipDTO(rs.getInt("SLIP_ID")
                , rs.getInt("ms_id")
                , rs.getString("slip_num")
                , rs.getInt("subleased_to")
                , rs.getString("MEMBERSHIP_ID"));
    }
    // this is custom so that we can pass the membership id of the owner of the slip to the supleasers storage tab
}
