package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.Email_InformationDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.rowmappers.EmailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmailRepositoryImpl implements EmailRepository {

    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmailRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Email_InformationDTO> getEmailInfo() {
        return null;
    }

    @Override
    public List<EmailDTO> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        return template.query(query, new EmailRowMapper());
    }

    @Override
    public EmailDTO getEmail(PersonDTO person) {
        return null;
    }

    @Override
    public int update(EmailDTO emailDTO) {
        String query = "UPDATE email SET " +
                "P_ID = :pId, " +
                "PRIMARY_USE = :isPrimaryUse, " +
                "EMAIL = :email, " +
                "EMAIL_LISTED = :isListed " +
                "WHERE EMAIL_ID = :email_id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(EmailDTO emailDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO email (P_ID, PRIMARY_USE, EMAIL, EMAIL_LISTED) " +
                "VALUES (:pId, :isPrimaryUse, :email, :isListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        emailDTO.setEmailId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(EmailDTO emailDTO) {
        String deleteSql = "DELETE FROM email WHERE EMAIL_ID = ?";
        return template.update(deleteSql, emailDTO.getEmailId());
    }
}
