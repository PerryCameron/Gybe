package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.repository.rowmappers.AppSettingsRowMapper;
import com.ecsail.Gybe.repository.rowmappers.ThemeRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public List<AppSettingsDTO> getAppSettingsByGroupName(String groupName) {
        String sql = "SELECT * FROM app_settings WHERE group_name LIKE ?";
        // Append the wildcard character to the groupName parameter
        String groupNamePattern = groupName + "%";
        return template.query(sql, new AppSettingsRowMapper(), groupNamePattern);
    }

    @Override
    public AppSettingsDTO getSettingFromKey(String key) {
        String sql = "SELECT * FROM app_settings WHERE setting_key = ?";
        try {
            return template.queryForObject(sql, new AppSettingsRowMapper(), key);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    @Override
    public AppSettingsDTO getSettingByGroup(String group) {
        String sql = "SELECT * FROM app_settings WHERE group_name = ?";
        try {
            return template.queryForObject(sql, new AppSettingsRowMapper(), group);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
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
    public AppSettingsDTO getSelectedYear() {
        return null;
    }
    @Override
    public ThemeDTO findThemeByYear(int year) {
        String sql = "SELECT * " +
                "FROM theme " +
                "WHERE year = IF(" +
                "    EXISTS(SELECT 1 FROM theme WHERE year = ?)," +
                "    ?," +
                "    0" +
                ") " +
                "LIMIT 1";

        return template.queryForObject(sql, new ThemeRowMapper(), year, year);
    }
}
