package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormRequestDTO;
import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import com.ecsail.Gybe.dto.HashDTO;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.rowmappers.FormHashRequestRowMapper;
import com.ecsail.Gybe.repository.rowmappers.FormRequestSummaryRowMapper;
import com.ecsail.Gybe.repository.rowmappers.HashRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class HashRepositoryImpl implements HashRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public HashRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<FormHashRequestDTO> getFormHashRequests(int year) {
        String sql = """
                SELECT
                    MAX(FORM_HASH_ID) AS FORM_HASH_ID,
                    MAX(REQ_DATE) AS REQ_DATE,
                    PRI_MEM,
                    LINK,
                    MSID,
                    MAILED_TO,
                    COUNT(*) AS DUPLICATE_COUNT
                FROM
                    form_hash_request
                WHERE
                        YEAR(REQ_DATE) = ?
                GROUP BY
                    PRI_MEM, LINK, MSID, MAILED_TO
                ORDER BY
                    REQ_DATE DESC;
                """;
        return template.query(sql, new FormHashRequestRowMapper(), year);
    }

    @Override
    public HashDTO getHashDTOFromMsid(int msid) {
        String QUERY = "select * from form_msid_hash where MS_ID=?";
        return (HashDTO) template.queryForObject(QUERY, new BeanPropertyRowMapper(HashDTO.class), new Object[]{msid});
    }

    @Override
    public HashDTO getHashDTOFromHash(long hash) {
        String QUERY = "select * from form_msid_hash where HASH=?";
        return (HashDTO) template.queryForObject(QUERY, new HashRowMapper(), hash);
    }

    @Override
    public HashDTO insertHash(HashDTO hashDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = "INSERT INTO form_msid_hash (HASH, MS_ID) VALUES (?, ?)";
        template.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, hashDTO.getHash());
                    ps.setInt(2, hashDTO.getMsId());
                    return ps;
                }, keyHolder);
        // Extract and set the generated HASH_ID
        if (keyHolder.getKey() != null) {
            hashDTO.setHash_id(keyHolder.getKey().intValue());
        }
        return hashDTO;
    }
    @Override
    public FormHashRequestDTO insertHashRequestHistory(FormHashRequestDTO formHashRequestDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = "INSERT INTO form_hash_request (PRI_MEM, LINK, MSID, MAILED_TO) VALUES (?, ?, ?, ?)";
        template.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, formHashRequestDTO.getPri_mem());
                    ps.setString(2, formHashRequestDTO.getLink());
                    ps.setInt(3, formHashRequestDTO.getMsid());
                    ps.setString(4, formHashRequestDTO.getMailed_to());
                    return ps;
                }, keyHolder);
        // Extract and set the generated FORM_HASH_ID
        if (keyHolder.getKey() != null) {
            formHashRequestDTO.setForm_hash_id(keyHolder.getKey().intValue());
        }
        return formHashRequestDTO;
    }

    @Override
    public List<FormRequestSummaryDTO> getFormRequestSummariesForYear(int year) {
        String sql = """
                SELECT
                    id.MEMBERSHIP_ID,
                    MAX(fhr.REQ_DATE) AS newest_hash_req_date,
                    MAX(fhr.PRI_MEM) AS pri_mem,
                    MAX(fhr.LINK) AS link,
                    MAX(fhr.MAILED_TO) AS mailed_to,
                    COUNT(DISTINCT fhr.FORM_HASH_ID) AS num_hash_duplicates,
                    MAX(fr.REQ_DATE) AS newest_form_req_date,
                    COUNT(DISTINCT fr.FORM_ID) AS num_form_attempts
                FROM
                    form_hash_request fhr
                        LEFT JOIN form_request fr ON fhr.MSID = fr.MSID
                        LEFT JOIN membership_id id ON fhr.MSID = id.MS_ID
                WHERE
                    (YEAR(fhr.REQ_DATE) = ? OR YEAR(fr.REQ_DATE) = ?)
                  AND id.FISCAL_YEAR = ?
                GROUP BY
                    fhr.MSID, id.MEMBERSHIP_ID
                ORDER BY
                    id.MEMBERSHIP_ID ASC;
                """;
        return template.query(sql, new Object[]{year, year, year}, new FormRequestSummaryRowMapper());
    }
    @Override
    public FormRequestDTO insertHashHistory(FormRequestDTO formRequestDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String INSERT_QUERY = "INSERT INTO form_request (PRI_MEM, MSID, SUCCESS) VALUES (?, ?, ?)";
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"FORM_ID"});
            ps.setString(1, formRequestDTO.getPrimaryMember());
            ps.setInt(2, formRequestDTO.getMsid());
            ps.setBoolean(3, formRequestDTO.isSuccess());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("FORM_ID")) {
            formRequestDTO.setForm_id((Integer) keyHolder.getKeys().get("FORM_ID"));
        }
        return formRequestDTO;
    }
}
