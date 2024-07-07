package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;
import com.ecsail.Gybe.repository.interfaces.PhoneRepository;
import com.ecsail.Gybe.repository.rowmappers.PhoneRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class PhoneRepositoryImpl implements PhoneRepository {

    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public PhoneRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PhoneDTO> getPhoneByPid(int pId) {
        String query = "SELECT * FROM phone Where P_ID = ?";
        return template.query(query, new PhoneRowMapper(), pId);
    }

    @Override
    public List<PhoneDTO> getPhoneByPerson(PersonDTO personDTO) {
        String query = "SELECT * FROM phone WHERE p_id = ?";
        return template.query(query, new PhoneRowMapper(), personDTO.getPId());
    }

    @Override
    public PhoneDTO getListedPhoneByType(PersonDTO p, String phoneType) {
        String query = "SELECT * FROM phone WHERE p_id = ? AND phone_listed = true AND phone_type = ? limit 1";
        try {
            return template.queryForObject(query, new PhoneRowMapper(), p.getPId(), phoneType);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no phone is found
        }
    }


    @Override
    public PhoneDTO getPhoneByPersonAndType(int pId, String type) {
        String query = "SELECT * FROM phone WHERE p_id = ? AND phone_type = ? LIMIT 1";
        try {
            return template.queryForObject(query, new PhoneRowMapper(), pId, type);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no phone is found
        }
    }


    @Override
    public int update(PhoneDTO phoneDTO) {
        String query = "UPDATE phone SET " +
                "P_ID = :pId," +
                "PHONE = :phone, " +
                "PHONE_TYPE = :phoneType, " +
                "PHONE_LISTED = :phoneListed " +
                "WHERE PHONE_ID = :phoneId";
        System.out.println("Phone ID is " + phoneDTO.getPhoneId());
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int delete(PhoneDTO phoneDTO) {
        String deleteSql = "DELETE FROM phone WHERE PHONE_ID = ?";
        return template.update(deleteSql, phoneDTO.getPhoneId());
    }

    @Override
    public int insert(PhoneDTO phoneDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO phone (P_ID, PHONE, PHONE_TYPE, PHONE_LISTED) " +
                "VALUES (:pId, :phone, :phoneType, :phoneListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        phoneDTO.setPhoneId(keyHolder.getKey().intValue());
        return affectedRows;
    }
}
