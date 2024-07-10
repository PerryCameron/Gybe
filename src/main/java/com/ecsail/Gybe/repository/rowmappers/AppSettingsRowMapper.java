package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.AppSettingsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppSettingsRowMapper implements RowMapper<AppSettingsDTO> {
//    public AppSettingDTO(String key, String value, String description, String dataType, Date dateTime, String groupName) {

    @Override
    public AppSettingsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppSettingsDTO(
                rs.getString("setting_key"),
                rs.getString("setting_value"),
                rs.getString("description"),
                rs.getString("data_type"),
                rs.getTimestamp("updated_at"),
                rs.getString("group_name"));
    }
}