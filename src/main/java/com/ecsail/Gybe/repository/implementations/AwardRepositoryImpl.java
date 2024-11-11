package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.AwardDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.AwardRepository;
import com.ecsail.Gybe.repository.rowmappers.AwardsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AwardRepositoryImpl implements AwardRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AwardRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<AwardDTO> getAwards(PersonDTO p) {
        String query = "SELECT * FROM awards WHERE p_id=" + p.getPId();
        return template.query(query, new AwardsRowMapper());
    }

    @Override
    public List<AwardDTO> getAwards() {
        String query = "SELECT * FROM awards";
        return template.query(query, new AwardsRowMapper());
    }

    @Override
    public int update(AwardDTO awardDTO) {
        System.out.println(awardDTO);
        String query = "UPDATE awards SET " +
                "P_ID = :pId, " +
                "AWARD_YEAR = :awardYear, " +
                "AWARD_TYPE = :awardType " +
                "WHERE AWARD_ID = :awardId ";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int batchUpdate(List<AwardDTO> awardDTOS) {
        String sql = "UPDATE awards SET awards.P_ID = :pId, AWARD_YEAR = :awardYear, AWARD_TYPE = :awardType WHERE AWARD_ID = :awardId";
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(awardDTOS.toArray());
        int[] result = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
        for (int num : result) {
            if (num != 1) {
                return 0; // Return 0 if any element is not 1
            }
        }
        System.out.println("Batch of awards successfully updated");
        return 1;
    }

    @Override
    public int insert(AwardDTO awardDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO awards (P_ID, AWARD_YEAR, AWARD_TYPE) VALUES (:pId, :awardYear, :awardType)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        awardDTO.setAwardId(keyHolder.getKey().intValue());
        return awardDTO.getAwardId();
    }

    @Override
    public int delete(AwardDTO awardDTO) {
        String deleteSql = "DELETE FROM awards WHERE Award_ID = ?";
        return template.update(deleteSql, awardDTO.getAwardId());
    }
}
