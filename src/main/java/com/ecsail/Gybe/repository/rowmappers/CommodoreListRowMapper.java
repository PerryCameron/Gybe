package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.PersonListDTO;
import com.ecsail.Gybe.pdf.enums.Pages;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommodoreListRowMapper implements RowMapper<PersonListDTO> {
    @Override
    public PersonListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonListDTO(
                rs.getInt("officer_year"),
                rs.getString("full_name"),
                Pages.PAST_COMMODORES
                );
    }
}

