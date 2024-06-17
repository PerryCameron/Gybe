package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.rowmappers.FormHashRequestRowMapper;
import com.ecsail.Gybe.repository.rowmappers.FormRequestSummaryRowMapper;
import com.ecsail.Gybe.repository.rowmappers.HashRowMapper;
import com.ecsail.Gybe.repository.rowmappers.UserAuthRequestRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Autowired
    public HashRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
        return template.queryForObject(QUERY, new HashRowMapper(), msid);
    }

    @Override
    public HashDTO getHashDTOFromHash(long hash) {
        String QUERY = "select * from form_msid_hash where HASH=?";
        return template.queryForObject(QUERY, new HashRowMapper(), hash);
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
                          COUNT(DISTINCT CASE WHEN YEAR(fhr.REQ_DATE) = ? THEN fhr.FORM_HASH_ID END) AS num_hash_duplicates,
                          MAX(fr.REQ_DATE) AS newest_form_req_date,
                          COUNT(DISTINCT CASE WHEN YEAR(fr.REQ_DATE) = ? THEN fr.FORM_ID END) AS num_form_attempts
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
                          id.MEMBERSHIP_ID;
                """;
        return template.query(sql, new Object[]{year, year, year, year, year}, new FormRequestSummaryRowMapper());
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
    @Override
    public int insertUserAuthRequest(String passKey, int pId) {
        String sql = "INSERT INTO user_auth_request (pass_key, pid, updated_at, completed) VALUES (?, ?, CURRENT_TIMESTAMP, '0000-00-00 00:00:00')";
        return template.update(sql, passKey, pId);
    }

    // probably not going to use below
    @Override
    public int timeStampCompleted(String passKey) {
        System.out.println("timeStampCompleted(String passKey)");
        String sql = "UPDATE user_auth_request SET completed = CURRENT_TIMESTAMP WHERE pass_key = ?";
        return template.update(sql, passKey);
    }
    @Override
    public boolean isValidKey(String passKey) {
        System.out.println("isValidKey(String passKey)");
        String sql = "SELECT COUNT(*) FROM user_auth_request WHERE pass_key = ? AND TIMESTAMPDIFF(MINUTE, updated_at, CURRENT_TIMESTAMP) < 10 AND completed = '0000-00-00 00:00:00'";
        Integer count = template.queryForObject(sql, Integer.class, passKey);
        return count != null && count > 0;
    }
    @Override
    public boolean existsUserAuthRequestByPidWithinTenMinutes(int pid) {
        System.out.println("existsUserAuthRequestByPidWithinTenMinutes(int pid)");
        String sql = "SELECT COUNT(*) FROM user_auth_request WHERE pid = ? AND TIMESTAMPDIFF(MINUTE, updated_at, CURRENT_TIMESTAMP) < 10 AND completed = '0000-00-00 00:00:00'";
        Integer count = template.queryForObject(sql, Integer.class, pid);
        return count != null && count > 0;
    }

    @Override
    public int updateUpdatedAtTimestamp(int pid) {
        System.out.println("updateUpdatedAtTimestamp(int pid)");
        // Update the updated_at field for the entry with the newest updated_at timestamp and the specified pid
        String sql = "UPDATE user_auth_request SET updated_at = CURRENT_TIMESTAMP WHERE pid = ? AND updated_at = (SELECT MAX(updated_at) FROM user_auth_request WHERE pid = ?)";
        return template.update(sql, pid, pid);
    }

    @Override
    public UserAuthRequestDTO findUserAuthRequestByPidWithinTenMinutes(int pid) {
        System.out.println("findUserAuthRequestByPidWithinTenMinutes(int pid)");
        // SQL query to select the record where the 'completed' timestamp is not filled
        // and the time difference between 'updated_at' and the current timestamp is less than 10 minutes
        String sql = "SELECT * FROM user_auth_request WHERE pid = ? AND completed = '0000-00-00 00:00:00' AND TIMESTAMPDIFF(MINUTE, updated_at, CURRENT_TIMESTAMP) < 10";
        try {
            return template.queryForObject(sql, new UserAuthRequestRowMapper(), pid);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Object was null");
            return null;
        }
    }

    @Override
    public int completeUserAuthRequest(int pid) {
        System.out.println("completeUserAuthRequest(int pid)");
        String sql = "UPDATE user_auth_request SET completed = CURRENT_TIMESTAMP WHERE pid = ? AND updated_at = (SELECT MAX(updated_at) FROM user_auth_request WHERE pid = ?)";
        return template.update(sql, pid, pid);
    }
}
