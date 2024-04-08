package com.ecsail.Gybe.repository.rowmappers;

import com.ecsail.Gybe.dto.UserAuthRequestDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


//public class UserAuthRequestRowMapper implements RowMapper<UserAuthRequestDTO> {
//    @Override
//    public UserAuthRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new UserAuthRequestDTO(
//                rs.getString("pass_key"),
//                rs.getInt("pid"),
//                rs.getDate("updated_at").toLocalDate(), // beginning of board term
//                rs.getDate("completed").toLocalDate());
//    }
//}
public class UserAuthRequestRowMapper implements RowMapper<UserAuthRequestDTO> {
    @Override
    public UserAuthRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        String passKey = rs.getString("pass_key");
        int pid = rs.getInt("pid");

        LocalDate updatedAt = null;
        Date updatedAtDate = rs.getDate("updated_at");
        if (updatedAtDate != null) {
            updatedAt = updatedAtDate.toLocalDate();
        }

        LocalDate completed = null;
        Date completedDate = rs.getDate("completed");
        if (completedDate != null) {
            completed = completedDate.toLocalDate();
        }

        return new UserAuthRequestDTO(passKey, pid, updatedAt, completed);
    }
}