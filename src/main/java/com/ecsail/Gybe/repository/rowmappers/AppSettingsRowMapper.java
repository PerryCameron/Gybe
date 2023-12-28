package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.AppSettingDTO;
import com.ecsail.Gybe.dto.BoatDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppSettingsRowMapper implements RowMapper<AppSettingDTO> {
//    public AppSettingDTO(String key, String value, String description, String dataType, Date dateTime, String groupName) {

    @Override
    public AppSettingDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppSettingDTO(
                rs.getString("setting_key"),
                rs.getString("setting_value"),
                rs.getString("description"),
                rs.getString("data_type"),
                rs.getDate("updated_at"),
                rs.getString("group_name"));
    }
}