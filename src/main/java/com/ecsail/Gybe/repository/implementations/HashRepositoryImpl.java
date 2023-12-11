package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.dto.HashDTO;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.rowmappers.FormHashRequestRowMapper;
import com.ecsail.Gybe.repository.rowmappers.FormSettingsRowMapper;
import com.ecsail.Gybe.repository.rowmappers.HashRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    public List<FormHashRequestDTO> getFormHashRequests() {
        String sql = "SELECT FORM_HASH_ID, REQ_DATE, PRI_MEM, LINK, MSID, MAILED_TO FROM form_hash_request";
        return template.query(sql, new FormHashRequestRowMapper());
    }
    @Override
    public HashDTO getHashDTOFromMsid(int msid) {
        String QUERY = "select * from form_msid_hash where MS_ID=?";
        return (HashDTO) template.queryForObject(QUERY, new BeanPropertyRowMapper(HashDTO.class), new Object [] {msid});
    }
    @Override
    public HashDTO getHashDTOFromHash(long hash) {
        String QUERY = "select * from form_msid_hash where HASH=?";
        return (HashDTO) template.queryForObject(QUERY, new HashRowMapper(), hash);
    }

    @Override
    public FormSettingsDTO getFormSettings() {
        String QUERY = "select * from form_settings";
        return template.queryForObject(QUERY, new FormSettingsRowMapper());
    }
}
