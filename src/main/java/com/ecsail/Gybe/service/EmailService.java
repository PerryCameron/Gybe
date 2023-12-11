package com.ecsail.Gybe.service;

import com.ecsail.Gybe.controller.AuthController;
import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.implementations.HashRepositoryImpl;
import com.ecsail.Gybe.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.Gybe.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.interfaces.InvoiceRepository;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Service
public class EmailService {
    private final HashRepository hashRepository;
    private final MembershipRepository membershipRepository;
    private final InvoiceRepository invoiceRepository;
    LinkBuilderService linkBuilder;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    @Autowired
    public EmailService(
            LinkBuilderService linkBuilder,
            HashRepositoryImpl hashRepository,
            MembershipRepositoryImpl membershipRepository,
            InvoiceRepositoryImpl invoiceRepository) {
        this.linkBuilder = linkBuilder;
        this.hashRepository = hashRepository;
        this.membershipRepository = membershipRepository;
        this.invoiceRepository = invoiceRepository;
    }

    // IMPLEMENT
    public String buildLinkWithParameters(String hash) {
        FormSettingsDTO formSettingsDTO = hashRepository.getFormSettings();
        HashDTO hashDTO = hashRepository.getHashDTOFromHash(Long.valueOf(hash));
        MembershipListDTO m = membershipRepository.getMembershipListFromMsidAndYear(formSettingsDTO.getSelected_year(), hashDTO.getMsId());
        logger.info("Serving form to membership " + m.getMembershipId() + " " + m.getFullName());
        m.setInvoiceDTOS((ArrayList<InvoiceDTO>) invoiceRepository.getInvoicesByMsidAndYear(m.getMsId(), formSettingsDTO.getSelected_year()));
        int invoiceId = m.getInvoiceDTOS().get(0).getId();
        m.getInvoiceDTOS().get(0).setInvoiceItems((ArrayList<InvoiceItemDTO>) invoiceRepository.getInvoiceItemsByInvoiceId(invoiceId));
        ArrayList<InvoiceItemDTO> items = m.getInvoiceDTOS().get(0).getInvoiceItems();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(formSettingsDTO.getForm_url())
                .path(formSettingsDTO.getForm_id())
                .queryParam("memid", m.getMembershipId())
                .queryParam("membershipType", m.getMemType())
                .queryParam("address[addr_line1]", m.getAddress())
                .queryParam("address[city]", m.getCity())
                .queryParam("address[state]", m.getState())
                .queryParam("address[postal]", m.getZip())
                .queryParam("workCredit", getInvoiceItemValue(items, "Work Credits"))
                .queryParam("winterStorage", "winterStorage", getInvoiceItemQty(items, "Winter Storage"))
                .queryParam("additionalCredit", getInvoiceItemValue(items, "Other Credit"))
                .queryParam("otherFee", getInvoiceItemValue(items, "Other Fee"))
                .queryParam("initiation", getInvoiceItemValue(items, "Initiation"))
                .queryParam("positionCredit", getInvoiceItemValue(items, "Position Credit"));
//                .queryParam("", "");
        String url = builder.toUriString();
        return url;
    }

    private String getInvoiceItemValue(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return  invoiceItemDTO.getValue();
    }

    private String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return String.valueOf(invoiceItemDTO.getQty());
    }

}
