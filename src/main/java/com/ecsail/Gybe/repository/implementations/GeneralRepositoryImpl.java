package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.StatsDTO;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.repository.rowmappers.StatsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GeneralRepositoryImpl implements GeneralRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GeneralRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean recordExists(String tableName, String columnName, int value) {
        String EXISTS_QUERY = "SELECT EXISTS(SELECT * FROM " + tableName + " WHERE " + columnName + "=?)";
        return Boolean.TRUE.equals(template.queryForObject(EXISTS_QUERY, Boolean.class, new Object[]{value}));
    }
    @Override
    public List<StatsDTO> getAllStats() {
        String sql = "SELECT * FROM stats";
        return template.query(sql, new StatsRowMapper());
    }

}