package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.InvoiceItemDTO;
import com.ecsail.Gybe.dto.PersonDTO;

import java.util.ArrayList;

public interface FormRequestService {
    String openForm(String hash);

    void populateModel(String hash);

    // IMPLEMENT
    String buildLinkWithParameters();

    //    .queryParam("", "")
    PersonDTO getPerson(ArrayList<PersonDTO> people, int personType);

    String getInvoiceItemValue(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName);

    String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName);
}
