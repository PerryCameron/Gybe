package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.SlipDTO;
import com.ecsail.Gybe.repository.interfaces.SlipRepository;
import com.ecsail.Gybe.repository.rowmappers.SlipRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class SlipRepositoryImpl implements SlipRepository {

    private final JdbcTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(SlipRepositoryImpl.class);


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
    @Override
    public SlipDTO getSlipFromMsid(int ms_id) {
        String QUERY = "select * from slip where MS_ID=?";
        try {
            return template.queryForObject(QUERY, new SlipRowMapper(), ms_id);
        } catch (EmptyResultDataAccessException e) {
            // Return a default instance of SlipDTO if no rows are found
            return new SlipDTO();
        }
    }

    @Override
    public boolean slipExists(int ms_id) {
        String QUERY = "SELECT COUNT(*) FROM slip WHERE MS_ID=?";
        Integer count = template.queryForObject(QUERY, Integer.class, ms_id);
        return count != null && count > 0;
    }

}
