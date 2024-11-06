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
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
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

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);

        int success = namedParameterJdbcTemplate.update(query, namedParameters); // Use NamedParameterJdbcTemplate here

        if (success > 0) {
            System.out.println("success in updating: " + phoneDTO);
        }

        return success;
    }

    @Override
    public int batchUpdate(List<PhoneDTO> phoneDTOList) {
        String sql = "UPDATE phone SET P_ID = :pId, PHONE = :phone, PHONE_TYPE = :phoneType, PHONE_LISTED = :phoneListed WHERE PHONE_ID = :phoneId";
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(phoneDTOList.toArray());
        int[] result = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
        for (int num : result) {
            if (num != 1) {
                return 0; // Return 0 if any element is not 1
            }
        }
        for(PhoneDTO phoneDTO : phoneDTOList) {
            System.out.println("Update success: " + phoneDTO);
        }
        return 1;
    }

    @Override
    public int delete(PhoneDTO phoneDTO) {
        System.out.println("Delete: " + phoneDTO);
        String deleteSql = "DELETE FROM phone WHERE PHONE_ID = ?";
        int affectedRows = template.update(deleteSql, phoneDTO.getPhoneId());
        if (affectedRows == 1) {
            System.out.println("Successfully Deleted " + phoneDTO);
        }
        return affectedRows;
    }

    @Override
    public int insert(PhoneDTO phoneDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO phone (P_ID, PHONE, PHONE_TYPE, PHONE_LISTED) " +
                "VALUES (:pId, :phone, :phoneType, :phoneListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        phoneDTO.setPhoneId(keyHolder.getKey().intValue());
        System.out.println("phone with id: " + phoneDTO.getPhoneId() + " added");
        return affectedRows;
    }


}
