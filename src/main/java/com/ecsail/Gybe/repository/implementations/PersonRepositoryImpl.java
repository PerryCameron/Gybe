package com.ecsail.Gybe.repository.implementations;


import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import com.ecsail.Gybe.repository.rowmappers.PersonRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class PersonRepositoryImpl implements PersonRepository {
    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PersonDTO> getActivePeopleByMsId(int msId) {
        String query = "SELECT * FROM person WHERE IS_ACTIVE=true AND ms_id=?";
        return template.query(query, new PersonRowMapper(), new Object[]{msId});
    }

    @Override
    public int update(PersonDTO personDTO) {
        String query = "UPDATE person SET " +
                "MS_ID = :msId, " +
                "member_type = :memberType, " +
                "F_NAME = :firstName, " +
                "L_NAME = :lastName, " +
                "OCCUPATION = :occupation, " +
                "BUSINESS = :business, " +
                "birthday = :birthday, " +
                "IS_ACTIVE = :active, " +
                "NICK_NAME = :nickName, " +
                "OLD_MSID = :oldMsid " +
                "WHERE p_id = :pId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(personDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(PersonDTO personDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query =
                """
                    INSERT INTO person (MS_ID, member_type, F_NAME, L_NAME, OCCUPATION, BUSINESS, 
                    birthday, IS_ACTIVE, NICK_NAME, OLD_MSID) VALUES (:msId, :memberType, :firstName,
                    :lastName, :occupation, :business, :birthday, :active, :nickName, :oldMsid)
                """;
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(personDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        personDTO.setpId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(PersonDTO personDTO) {
        String deleteSql = "DELETE FROM person WHERE P_ID = ?";
        return template.update(deleteSql, personDTO.getpId());
    }

    @Override
    public List<PersonDTO> getChildrenByMsId(int msId) {
        String QUERY = "select * from person p left join membership m on m.MS_ID = p.MS_ID where p.MEMBER_TYPE=3" +
                " and p.IS_ACTIVE=true and m.MS_ID=?";
        List<PersonDTO> personDTOS = template.query(QUERY, new PersonRowMapper(), msId);
        return personDTOS;
    }

}
