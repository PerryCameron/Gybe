package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.repository.rowmappers.AppSettingsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SettingsRepositoryImpl implements SettingsRepository {

    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public SettingsRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public List<AppSettingDTO> getAppSettingsByGroupName(String groupName) {
        String sql = "SELECT * FROM app_settings WHERE group_name = ?";
        return template.query(sql, new AppSettingsRowMapper(), groupName);
    }

    @Override
    public List<DbRosterSettingsDTO> getSearchableListItems() {
        return null;
    }

    @Override
    public List<MembershipListRadioDTO> getRadioChoices() {
        return null;
    }

    @Override
    public List<DbBoatSettingsDTO> getBoatSettings() {
        return null;
    }

    @Override
    public List<BoatListRadioDTO> getBoatRadioChoices() {
        return null;
    }
}
