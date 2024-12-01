package com.ecsail.Gybe.repository.implementations;


import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.InvoiceRepository;
import com.ecsail.Gybe.repository.rowmappers.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InvoiceRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<InvoiceDTO> getInvoicesByMsid(int msId) {
        String query = "SELECT * FROM invoice WHERE ms_id=?";
        return template.query(query, new InvoiceRowMapper(), msId);
    }

    @Override
    public List<InvoiceDTO> getInvoicesByMsidAndYear(int msId, int year) {
        String query = "SELECT * FROM invoice WHERE ms_id=? and FISCAL_YEAR=? limit 1";
        return template.query(query, new InvoiceRowMapper(), new Object[]{msId, year});
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        String query = "SELECT * FROM invoice";
        return template.query(query, new InvoiceRowMapper());
    }

    @Override
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d) {
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i " +
                "left join person p on i.MS_ID = p.MS_ID " +
                "left join membership_id mi on i.MS_ID = mi.MS_ID " +
                "where i.FISCAL_YEAR=? and mi.FISCAL_YEAR=? and p.MEMBER_TYPE=1 and i.COMMITTED=true and i.batch=?";
        return template.query(query, new InvoiceWithMemberInfoRowMapper(), new Object[]{d.getFiscalYear(), d.getFiscalYear(), d.getBatch()});
    }

    @Override
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year) {
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i " +
                "left join person p on i.MS_ID = p.MS_ID " +
                "left join membership_id mi on i.MS_ID = mi.MS_ID " +
                "where i.FISCAL_YEAR=? and mi.FISCAL_YEAR=? and p.MEMBER_TYPE=1 and i.COMMITTED=true";
        return template.query(query, new InvoiceWithMemberInfoRowMapper(), new Object[] {year,year});
    }

    @Override
    public List<InvoiceItemDTO> getInvoiceItemsByInvoiceId(int id) {
        String query = "SELECT * FROM invoice_item WHERE invoice_id=?";
        return template.query(query, new InvoiceItemRowMapper(), id);
    }

    @Override
    public List<PaymentDTO> getPaymentByInvoiceId(int id) {
        String query = "SELECT * from payment where INVOICE_ID=?";
        return template.query(query, new PaymentRowMapper(), id);
    }

    @Override
    public int insert(PaymentDTO paymentDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
                String query = "INSERT INTO payment ( " +
                        "INVOICE_ID, " +
                        "CHECK_NUMBER, " +
                        "PAYMENT_TYPE, " +
                        "PAYMENT_DATE, " +
                        "AMOUNT, " +
                        "DEPOSIT_ID) " +
                "VALUES (:invoiceId, :checkNumber, :paymentType, :paymentDate, :PaymentAmount, :depositId)";
                SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(paymentDTO);
                int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        paymentDTO.setPayId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    public int insert(InvoiceDTO invoiceDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO invoice ( " +
                "MS_ID, FISCAL_YEAR, PAID, TOTAL, CREDIT, BALANCE, BATCH, COMMITTED, CLOSED, " +
                "SUPPLEMENTAL, MAX_CREDIT)" +
                "VALUES (:msId, :year, :paid, :total, :credit, :balance, :batch, :committed, :closed, " +
                ":supplemental, :maxCredit)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(invoiceDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        invoiceDTO.setId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public  int getBatchNumber(String year) {
        String query = "SELECT MAX(batch) FROM invoice WHERE committed=true AND fiscal_year=:year";
        Map<String, Object> params = Collections.singletonMap("year", year);
        return Optional.ofNullable(template.queryForObject(query, Integer.class, params)).orElse(0);
    }

    @Override
    public List<FeeDTO> getFeesFromYear(int year) {
        String query = "SELECT * FROM fee WHERE fee_year=?";
        return template.query(query, new FeeRowMapper(), year);
    }

    @Override
    public List<FeeDTO> getFeesByType(String type) {
        String query = "SELECT * FROM fee WHERE FIELD_NAME=?";
        return template.query(query, new FeeRowMapper(), type);
    }

    @Override
    public int update(InvoiceDTO invoiceDTO) {
        String query = "UPDATE invoice SET " +
                "MS_ID = :msId, " +
                "FISCAL_YEAR = :year, " +
                "PAID = :paid, " +
                "TOTAL = :total, " +
                "CREDIT = :credit, " +
                "BALANCE = :balance, " +
                "BATCH = :batch, " +
                "COMMITTED = :committed, " +
                "CLOSED = :closed, " +
                "SUPPLEMENTAL = :supplemental, " +
                "MAX_CREDIT = :maxCredit " +
                "WHERE ID = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(invoiceDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int[] updateBatch(InvoiceDTO invoiceDTO) {
        return new int[0];
    }

    @Override
    public int[] insertBatch(InvoiceDTO invoiceDTO) {
        return new int[0];
    }

    @Override
    public int update(PaymentDTO paymentDTO) {
        String query = "UPDATE payment set " +
                "INVOICE_ID = :invoiceId, " +
                "CHECK_NUMBER = :checkNumber, " +
                "PAYMENT_TYPE = :paymentType, " +
                "PAYMENT_DATE = :paymentDate, " +
                "AMOUNT = :PaymentAmount, " +
                "DEPOSIT_ID = :depositId " +
                "WHERE PAY_ID = :payId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(paymentDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int delete(PaymentDTO paymentDTO) {
        String query = "DELETE FROM payment WHERE PAY_ID = ?";
        return template.update(query, paymentDTO.getPayId());
    }

    @Override
    public boolean exists(MembershipListDTO membershipListDTO, int year) {
        String query = "SELECT EXISTS(SELECT * FROM invoice WHERE ms_id=? AND fiscal_year=?)";
        return template.queryForObject(query, Boolean.class, membershipListDTO.getMsId(), year);
    }

    @Override
    public int deletePaymentsByInvoiceID(InvoiceDTO invoiceDTO) {
        String query = "DELETE FROM payment WHERE INVOICE_ID = ?";
        return template.update(query, invoiceDTO.getId());
    }

    @Override
    public int deleteItemsByInvoiceID(InvoiceDTO invoiceDTO) {
        String query = "DELETE FROM invoice_item WHERE INVOICE_ID = ?";
        return template.update(query, invoiceDTO.getId());
    }
    @Override
    public int delete(InvoiceDTO invoiceDTO) {
        String query = "DELETE FROM invoice WHERE ID = ?";
        return template.update(query, invoiceDTO.getId());
    }
}
