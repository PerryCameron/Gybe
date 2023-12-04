package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.repository.rowmappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AuthenticationRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        String query = "SELECT * from users where username=?";
        try {
            UserDTO user = template.queryForObject(query, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    @Override
//    public List<AwardDTO> getAwards(PersonDTO p) {
//        String query = "SELECT * FROM awards WHERE p_id=" + p.getpId();
//        return template.query(query, new AwardsRowMapper());
//    }
//
//    @Override
//    public List<AwardDTO> getAwards() {
//        String query = "SELECT * FROM awards";
//        return template.query(query, new AwardsRowMapper());
//    }
//
//    @Override
//    public int update(AwardDTO awardDTO) {
//        String query = "UPDATE awards SET " +
//                "P_ID = :pId, " +
//                "AWARD_YEAR = :awardYear, " +
//                "AWARD_TYPE = :awardType " +
//                "WHERE AWARD_ID = :awardId ";
//        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
//        return namedParameterJdbcTemplate.update(query, namedParameters);
//    }
//
//    @Override
//    public int insert(AwardDTO awardDTO) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        String query = "INSERT INTO awards (P_ID, AWARD_YEAR, AWARD_TYPE) VALUES (:pId, :awardYear, :awardType)";
//        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
//        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
//        awardDTO.setAwardId(keyHolder.getKey().intValue());
//        return affectedRows;
//    }
//
//    @Override
//    public int delete(AwardDTO awardDTO) {
//        String deleteSql = "DELETE FROM awards WHERE Award_ID = ?";
//        return template.update(deleteSql, awardDTO.getAwardId());
//    }
}
