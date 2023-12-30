package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.repository.rowmappers.AppSettingsRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SettingsRepositoryImpl implements SettingsRepository {

    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SettingsRepository.class);



    public SettingsRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public List<AppSettingDTO> getAppSettingsByGroupName(String groupName) {
        String sql = "SELECT * FROM app_settings WHERE group_name LIKE ?";
        // Append the wildcard character to the groupName parameter
        String groupNamePattern = groupName + "%";
        return template.query(sql, new AppSettingsRowMapper(), groupNamePattern);
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

    @Override
    public AppSettingDTO getSelectedYear() {
        return null;
    }
}
