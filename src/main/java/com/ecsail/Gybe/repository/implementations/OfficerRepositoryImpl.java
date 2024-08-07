package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.LeadershipDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
import com.ecsail.Gybe.dto.OfficerWithNameDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.OfficerRepository;
import com.ecsail.Gybe.repository.rowmappers.LeadershipRowMapper;
import com.ecsail.Gybe.repository.rowmappers.OfficerRowMapper;
import com.ecsail.Gybe.repository.rowmappers.OfficerWithNamesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class OfficerRepositoryImpl implements OfficerRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public OfficerRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<OfficerDTO> getOfficers() {
        String query = "SELECT * FROM officer";
        return template.query(query,new OfficerRowMapper());
    }

    @Override
    public List<OfficerDTO> getOfficer(String field, int attribute) {
        String query = "SELECT * FROM officer WHERE ? = ?";
        return template.query(query,new OfficerRowMapper(),field,attribute);    }

    @Override
    public List<OfficerDTO> getOfficer(PersonDTO person) {
        String query = "SELECT * FROM officer WHERE P_ID = ?";
        return template.query(query,new OfficerRowMapper(), person.getPId());
    }

    @Override
    public List<OfficerWithNameDTO> getOfficersWithNames(String type) {
        String query = "SELECT f_name,L_NAME,off_year FROM officer o LEFT JOIN person p ON o.p_id=p.p_id WHERE off_type= ?";
        return template.query(query,new OfficerWithNamesRowMapper(), type);
    }

    @Override
    public List<LeadershipDTO> getLeadershipByYear(int year) {
        String query = """
                select o.O_ID,p.P_ID,p.F_NAME,p.L_NAME,b.position,b.is_officer,b.is_chair,b.is_assistant_chair,b.list_order,o.BOARD_YEAR 
                from officer o
                left join person p on p.P_ID = o.P_ID
                left join board_positions b on b.identifier=o.OFF_TYPE where OFF_YEAR=?
                """;
        return template.query(query,new LeadershipRowMapper(), year);
    }

    @Override
    public int update(OfficerDTO officerDTO) {
        String query = "UPDATE officer SET " +
                "P_ID = :pId, " +
                "BOARD_YEAR = :boardYear, " +
                "OFF_TYPE = :officerType, " +
                "OFF_YEAR = :fiscalYear " +
                "WHERE O_ID = :officerId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(officerDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(OfficerDTO officerDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO officer (P_ID, BOARD_YEAR, OFF_TYPE, OFF_YEAR) " +
                "VALUES (:pId, :boardYear, :officerType, :fiscalYear)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pId", officerDTO.getpId());
        namedParameters.addValue("boardYear", officerDTO.getBoardYear());
        namedParameters.addValue("officerType", officerDTO.getOfficerType());
        namedParameters.addValue("fiscalYear", officerDTO.getFiscalYear());
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        officerDTO.setOfficerId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(OfficerDTO officerDTO) {
        String deleteSql = "DELETE FROM officer WHERE O_ID = ?";
        return template.update(deleteSql, officerDTO.getOfficerId());
    }
}
