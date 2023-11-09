package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.HashDTO;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.rowmappers.HashRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class HashRepositoryImpl implements HashRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public HashRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

//    public void getAuthDTOFromEmail(AuthDTO authDTO) {
//        String QUERY = "SELECT p.p_id,p.ms_id,p.member_type,p.f_name,p.l_name,e.email,p.nick_name" +
//                " FROM email e LEFT JOIN person p ON e.P_ID=p.P_ID LEFT JOIN membership_id id " +
//                "ON p.MS_ID=id.MS_ID WHERE id.FISCAL_YEAR=" + SpinnakerPackagedApplication.nextFiscalYear + " AND e.EMAIL=?";
//        AuthDTO newAuthDto =  (AuthDTO) template.queryForObject(QUERY, new BeanPropertyRowMapper(AuthDTO.class),
//                new Object [] {authDTO.getEmail()});
//        authDTO.copy(newAuthDto);
//        authDTO.setExists(true);
//    }

    public HashDTO getHashDTOFromMsid(int msid) {
        String QUERY = "select * from form_msid_hash where MS_ID=?";
        return (HashDTO) template.queryForObject(QUERY, new BeanPropertyRowMapper(HashDTO.class), new Object [] {msid});
    }
    @Override
    public HashDTO getHashDTOFromHash(long hash) {
        String QUERY = "select * from form_msid_hash where HASH=?";
        return (HashDTO) template.queryForObject(QUERY, new HashRowMapper(), hash);
    }

}
