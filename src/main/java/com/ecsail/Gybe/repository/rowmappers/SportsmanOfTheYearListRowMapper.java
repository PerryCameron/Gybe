package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.PersonListDTO;
import com.ecsail.Gybe.pdf.enums.Pages;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SportsmanOfTheYearListRowMapper implements RowMapper<PersonListDTO> {
    @Override
    public PersonListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonListDTO(
                rs.getInt("award_year"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Pages.SPORTSMANSHIP_AWARD
                );
    }
}

