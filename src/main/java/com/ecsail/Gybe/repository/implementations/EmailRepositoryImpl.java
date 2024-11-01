package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.Email_InformationDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.rowmappers.AuthRowMapper;
import com.ecsail.Gybe.repository.rowmappers.EmailInformationRowMapper;
import com.ecsail.Gybe.repository.rowmappers.EmailRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmailRepositoryImpl implements EmailRepository {

    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(EmailRepositoryImpl.class);

    @Autowired
    public EmailRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Email_InformationDTO> getEmailInfo() {
        String sql = "SELECT id.membership_id, m.join_date, p.l_name, p.f_name, email, primary_use " +
                "FROM email e " +
                "INNER JOIN person p ON p.p_id = e.p_id " +
                "INNER JOIN membership m ON m.ms_id = p.ms_id " +
                "INNER JOIN membership_id id ON id.ms_id = m.ms_id " +
                "WHERE id.fiscal_year = YEAR(NOW()) AND id.renew = true " +
                "ORDER BY id.membership_id";
        return template.query(sql, new EmailInformationRowMapper());
    }

    @Override
    public List<EmailDTO> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        System.out.println("query is " + query);
        return template.query(query, new EmailRowMapper());
    }

    @Override
    public EmailDTO getPrimaryEmail(PersonDTO person) {
        String query = "select * from email where P_ID=? and PRIMARY_USE=true limit 1;";
        try {
            return template.queryForObject(query, new EmailRowMapper(), person.getPId());
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no email is found
        }
    }

    @Override
    public int update(EmailDTO emailDTO) {
        String query = "UPDATE email SET " +
                "PRIMARY_USE = :primaryUse, " +
                "EMAIL = :email, " +
                "EMAIL_LISTED = :emailListed " +
                "WHERE EMAIL_ID = :emailId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int batchUpdate(List<EmailDTO> emailDTOList) {
        String sql = "UPDATE email SET PRIMARY_USE = :primaryUse, EMAIL = :email, EMAIL_LISTED = :emailListed WHERE EMAIL_ID = :emailId";
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(emailDTOList.toArray());
        int[] result = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
        for (int num : result) {
            if (num != 1) {
                return 0; // Return 0 if any element is not 1
            }
        }
        return 1;
    }

    @Override
    public int insert(EmailDTO emailDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO email (P_ID, PRIMARY_USE, EMAIL, EMAIL_LISTED) " +
                "VALUES (:pId, :primaryUse, :email, :emailListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        emailDTO.setEmailId(keyHolder.getKey().intValue()); // Get generated key
        return emailDTO.getEmailId();
    }

    @Override
    public int delete(EmailDTO emailDTO) {
        String deleteSql = "DELETE FROM email WHERE EMAIL_ID = ?";
        return template.update(deleteSql, emailDTO.getEmailId());
    }

    @Override
    public boolean emailFromActiveMembershipExists(String email, int year) {
        if (email == null || email.isEmpty()) {
            logger.error("Email {}doesn't exist", email);
            return false;
        }
        String existsQuery = "SELECT EXISTS(SELECT * FROM email e " +
                "LEFT JOIN person p ON e.P_ID = p.P_ID " +
                "LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID " +
                "WHERE id.FISCAL_YEAR = ? AND e.EMAIL = ?)";
        try {
            Boolean result = template.queryForObject(existsQuery, Boolean.class, year, email);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public AuthDTO getAuthDTOFromEmail(int year, String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email parameter is null or empty");
            return null;
        }
        String query = "SELECT p.p_id, p.ms_id, p.member_type, p.f_name, p.l_name, e.email, p.nick_name " +
                "FROM email e " +
                "LEFT JOIN person p ON e.P_ID = p.P_ID " +
                "LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID " +
                "WHERE id.FISCAL_YEAR = ? AND e.EMAIL = ?";
        try {
            return template.queryForObject(
                    query,
                    new AuthRowMapper(), // Use your custom RowMapper implementation
                    year,
                    email
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateAuthDTOFromEmail(int year, AuthDTO authDTO) {
        if (authDTO == null || authDTO.getEmail() == null || authDTO.getEmail().isEmpty()) {
            logger.error("AuthDTO or email is null or empty");
            return;
        }
        String email = authDTO.getEmail();
        System.out.println("Using year " + year + " and email=" + email);
        String query = "SELECT p.p_id, p.ms_id, p.member_type, p.f_name, p.l_name, e.email, p.nick_name " +
                "FROM email e " +
                "LEFT JOIN person p ON e.P_ID = p.P_ID " +
                "LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID " +
                "WHERE id.FISCAL_YEAR = ? AND e.EMAIL = ?";
        try {
            AuthDTO result = template.queryForObject(
                    query,
                    new AuthRowMapper(), // Use your custom RowMapper implementation
                    year,
                    email
            );
            if (result != null) {
                authDTO.copyFrom(result); // Assuming there is a method to copy properties from another AuthDTO
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM email WHERE EMAIL = ?";
        Integer count = template.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
