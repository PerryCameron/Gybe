package com.ecsail.Gybe.repository.rowmappers;


import com.ecsail.Gybe.dto.HashDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HashRowMapper implements RowMapper<HashDTO> {
    @Override
    public HashDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HashDTO(
                rs.getInt("HASH_ID"),
                rs.getLong("HASH"),
                rs.getInt("MS_ID"));
    }
}
// here are fields from POJO HashDTO
//    private int hash_id;
//    private long hash;
//    private int ms_id;

// here is table that it maps to
//create table form_msid_hash
//        (
//                HASH_ID int auto_increment
//                primary key,
//                HASH    bigint not null,
//                MS_ID   int    not null,
//                constraint HASH
//        unique (HASH),
//    constraint form_msid_hash_ibfk_1
//    foreign key (MS_ID) references membership (MS_ID)
//        )
// Why is this wrong?