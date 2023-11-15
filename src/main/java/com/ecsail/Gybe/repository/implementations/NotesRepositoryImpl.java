package com.ecsail.Gybe.repository.implementations;


import com.ecsail.Gybe.dto.InvoiceWithMemberInfoDTO;
import com.ecsail.Gybe.dto.NotesDTO;
import com.ecsail.Gybe.repository.interfaces.NotesRepository;
import com.ecsail.Gybe.repository.rowmappers.MemoRowMapper;
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
public class NotesRepositoryImpl implements NotesRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate template;

    public NotesRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<NotesDTO> getMemosByMsId(int msId) {
        String query = "SELECT * FROM memo";
        if (msId != 0) query += " WHERE ms_id=?";
        List<NotesDTO> notesDTOS =
                template.query(query, new MemoRowMapper(), new Object[]{msId});
        return notesDTOS;
    }

    @Override
    public List<NotesDTO> getMemosByBoatId(int boatId) {
        String query = "SELECT * FROM memo WHERE boat_id=?";
        List<NotesDTO> notesDTOS =
                template.query(query, new MemoRowMapper(), new Object[]{boatId});
        return notesDTOS;
    }

    @Override
    public NotesDTO getMemoByInvoiceIdAndCategory(InvoiceWithMemberInfoDTO invoice, String category) {
                String query = "SELECT * FROM memo WHERE INVOICE_ID=" + invoice.getId() + " AND category='" + category + "'";

//        String query = "SELECT * FROM memo WHERE INVOICE_ID=:invoiceId AND category=:category";
//        SqlParameterSource params = new MapSqlParameterSource()
//                .addValue("invoiceId", invoice.getId())
//                .addValue("category", category);
        NotesDTO notesDTO =
                template.queryForObject(query, new MemoRowMapper());
        return notesDTO;
    }

//    @Override
//    public List<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
//        String fiscalYear = year;
//        String query = "SELECT * FROM memo "
//                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
//                + "WHERE YEAR(memo_date)=:year and id.fiscal_year=:fiscalYear and memo.CATEGORY IN(:category)";
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("year", year)
//                .addValue("fiscalYear", fiscalYear)
//                .addValue("category", category);
//        List<Memo2DTO> memoDTOs =
//                template.query(query, new Memo2RowMapper(), namedParameters);
//        return memoDTOs;
//    }



    @Override
    public int insertNote(NotesDTO notesDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO memo (MS_ID, MEMO_DATE, MEMO, INVOICE_ID, CATEGORY, BOAT_ID) " +
                "VALUES (:msId, :memoDate, :memo, :invoiceId, :category, :boatId)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(notesDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        notesDTO.setMemoId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int update(NotesDTO notesDTO) {
        String query = "UPDATE memo SET " +
                "MS_ID = :msId, " +
                "MEMO_DATE = :memoDate, " +
                "MEMO = :memo, " +
                "INVOICE_ID = :invoiceId, " +
                "CATEGORY = :category, " +
                "BOAT_ID = :boatId " +
                "WHERE " +
                "MEMO_ID = :memoId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(notesDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int delete(NotesDTO notesDTO) {
        String sql = "DELETE FROM memo WHERE MEMO_ID = ?";
        try {
            return template.update(sql, notesDTO.getMemoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
