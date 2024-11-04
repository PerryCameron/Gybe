package com.ecsail.Gybe.repository.implementations;


import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PersonListDTO;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import com.ecsail.Gybe.repository.rowmappers.CommodoreListRowMapper;
import com.ecsail.Gybe.repository.rowmappers.CommodoreMessageRowMapper;
import com.ecsail.Gybe.repository.rowmappers.PersonRowMapper;
import com.ecsail.Gybe.repository.rowmappers.SportsmanOfTheYearListRowMapper;
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
public class PersonRepositoryImpl implements PersonRepository {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PersonDTO> getActivePeopleByMsId(int msId) {
        String query = "SELECT * FROM person WHERE IS_ACTIVE=true AND ms_id=?";
        return template.query(query, new PersonRowMapper(), msId);
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
        int success = 0;
        try {
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(personDTO);
            success =  namedParameterJdbcTemplate.update(query, namedParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
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
        personDTO.setPId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(PersonDTO personDTO) {
        String deleteSql = "DELETE FROM person WHERE P_ID = ?";
        return template.update(deleteSql, personDTO.getPId());
    }

    @Override
    public List<PersonDTO> getChildrenByMsId(int msId) {
        String QUERY = "select * from person p left join membership m on m.MS_ID = p.MS_ID where p.MEMBER_TYPE=3" +
                " and p.IS_ACTIVE=true and m.MS_ID=?";
        return template.query(QUERY, new PersonRowMapper(), msId);
    }

    @Override
    public PersonDTO getPersonByEmail(String email) {
        String query = """
                select p.P_ID,p.MS_ID,p.MEMBER_TYPE,p.F_NAME,p.L_NAME,p.BIRTHDAY,
                       p.OCCUPATION,p.BUSINESS,p.IS_ACTIVE,p.NICK_NAME,p.OLD_MSID from email e
                         left join person p on e.P_ID = p.P_ID
                         where EMAIL=?
                """;
        try {
            return template.queryForObject(query, new PersonRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return new PersonDTO("none");
        }
    }

    @Override
    public CommodoreMessageDTO getCommodoreMessageByYear(int year) {
        String sql = "SELECT * FROM commodore_message WHERE fiscal_year = ?";
        return template.queryForObject(sql, new Object[]{year}, new CommodoreMessageRowMapper());
    }

    @Override
    public List<PersonListDTO> getAllCommodores() {
        String query = """    
                SELECT
                    CONCAT(p.f_name, ' ', p.L_NAME) AS full_name,
                    o.off_year AS officer_year
                FROM 
                    officer o
                LEFT JOIN 
                    person p ON o.p_id = p.p_id
                WHERE 
                    o.off_type = 'CO' 
                    AND o.off_year <> YEAR(CURDATE());
                                                    """;
        return template.query(query, new CommodoreListRowMapper());
    }

    @Override
    public List<PersonListDTO> getAllSportsManOfTheYear() {
        String query = """    
                SELECT
                    GROUP_CONCAT(CONCAT(f_name, ' ', L_NAME) ORDER BY f_name, L_NAME SEPARATOR '/') AS full_name,
                    AWARD_YEAR AS award_year
                FROM awards o
                LEFT JOIN person p
                ON o.p_id = p.p_id
                WHERE AWARD_TYPE = 'SA'
                GROUP BY AWARD_YEAR;
                                """;
        return template.query(query, new SportsmanOfTheYearListRowMapper());
    }
}
