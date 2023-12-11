package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.*;

import java.util.List;
import java.util.Set;

public interface InvoiceRepository {
    List<InvoiceDTO> getInvoicesByMsid(int ms_id);

    List<InvoiceDTO> getInvoicesByMsidAndYear(int msId, int year);

    List<InvoiceDTO> getAllInvoices();
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d);
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year);
    List<InvoiceItemDTO> getInvoiceItemsByInvoiceId(int id);
    List<PaymentDTO> getPaymentByInvoiceId(int id);
    int insert(PaymentDTO paymentDTO);
    int insert(InvoiceDTO invoiceDTO);
    int getBatchNumber(String year);
    List<FeeDTO> getFeesFromYear(int year);
    int update(InvoiceDTO invoiceDTO);
    int[] updateBatch(InvoiceDTO invoiceDTO);
    int[] insertBatch(InvoiceDTO invoiceDTO);
    int update(PaymentDTO paymentDTO);
    int delete(PaymentDTO paymentDTO);
    boolean exists(MembershipListDTO membershipListDTO, int year);
    int deletePaymentsByInvoiceID(InvoiceDTO invoiceDTO);
    int deleteItemsByInvoiceID(InvoiceDTO invoiceDTO);
    int delete(InvoiceDTO invoiceDTO);
}
