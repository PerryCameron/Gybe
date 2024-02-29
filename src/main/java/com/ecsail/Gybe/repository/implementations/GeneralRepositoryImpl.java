package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.AgesDTO;
import com.ecsail.Gybe.dto.StatsDTO;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.repository.rowmappers.AgeRowMapper;
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

    @Override
    public AgesDTO getAges() {
        String query = """
                SELECT
                    SUM(IF(age BETWEEN 0 and 10,1,0)) as '0 - 10',
                    SUM(IF(age BETWEEN 11 and 20,1,0)) as '11 - 20',
                    SUM(IF(age BETWEEN 21 and 30,1,0)) as '21 - 30',
                    SUM(IF(age BETWEEN 31 and 40,1,0)) as '31 - 40',
                    SUM(IF(age BETWEEN 41 and 50,1,0)) as '41 - 50',
                    SUM(IF(age BETWEEN 51 and 60,1,0)) as '51 - 60',
                    SUM(IF(age BETWEEN 61 and 70,1,0)) as '61 - 70',
                    SUM(IF(age BETWEEN 71 and 80,1,0)) as '71 - 80',
                    SUM(IF(age BETWEEN 81 and 90,1,0)) as '81 - 90',
                    SUM(IF(age IS NULL, 1, 0)) as 'Not Reported',
                    COUNT(*) AS 'Total People'
                FROM
                    (SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), p.BIRTHDAY)), '%Y') AS age
                     FROM person p
                              LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID
                     WHERE id.FISCAL_YEAR = YEAR(now()) AND id.RENEW = 1 AND p.IS_ACTIVE = 1) AS derived;
                """;
        return template.queryForObject(query, new AgeRowMapper());
    }
}