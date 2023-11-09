package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.SlipDTO;
import com.ecsail.Gybe.repository.interfaces.SlipRepository;
import com.ecsail.Gybe.repository.rowmappers.SlipRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SlipRepositoryImpl implements SlipRepository {

    private final JdbcTemplate template;

    public SlipRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public SlipDTO getSlip(int msId) {
        String sql = "select * from slip where MS_ID = ? or SUBLEASED_TO = ? LIMIT 1";
        try {
            return template.queryForObject(sql, new SlipRowMapper(), msId, msId);
        } catch (EmptyResultDataAccessException e) {
            return new SlipDTO();
        }
    }
}
