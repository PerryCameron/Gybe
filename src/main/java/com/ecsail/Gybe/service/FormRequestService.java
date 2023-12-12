package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.implementations.*;
import com.ecsail.Gybe.repository.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Service
public class FormRequestService {
    private final HashRepository hashRepository;
    private final MembershipRepository membershipRepository;
    private final InvoiceRepository invoiceRepository;
    private final PersonRepository personRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    LinkBuilderService linkBuilder;
    private static final Logger logger = LoggerFactory.getLogger(FormRequestService.class);


    @Autowired
    public FormRequestService(
            LinkBuilderService linkBuilder,
            HashRepositoryImpl hashRepository,
            MembershipRepositoryImpl membershipRepository,
            InvoiceRepositoryImpl invoiceRepository,
            PersonRepositoryImpl personRepository,
            EmailRepositoryImpl emailRepository,
            PhoneRepositoryImpl phoneRepository) {
        this.linkBuilder = linkBuilder;
        this.hashRepository = hashRepository;
        this.membershipRepository = membershipRepository;
        this.invoiceRepository = invoiceRepository;
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
    }

    // IMPLEMENT
    public String buildLinkWithParameters(String hash) {
        FormSettingsDTO formSettingsDTO = hashRepository.getFormSettings();
        HashDTO hashDTO = hashRepository.getHashDTOFromHash(Long.valueOf(hash));
        // get the membership
        MembershipListDTO m = membershipRepository.getMembershipListFromMsidAndYear(formSettingsDTO.getSelected_year(), hashDTO.getMsId());
        logger.info("Serving form to membership " + m.getMembershipId() + " " + m.getFullName());
        // get the invoices
        m.setInvoiceDTOS((ArrayList<InvoiceDTO>) invoiceRepository.getInvoicesByMsidAndYear(m.getMsId(), formSettingsDTO.getSelected_year()));
        int invoiceId = m.getInvoiceDTOS().get(0).getId();
        m.getInvoiceDTOS().get(0).setInvoiceItems((ArrayList<InvoiceItemDTO>) invoiceRepository.getInvoiceItemsByInvoiceId(invoiceId));
        ArrayList<InvoiceItemDTO> items = m.getInvoiceDTOS().get(0).getInvoiceItems();
        // get the people
        m.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(m.getMsId()));
        // primary
        PersonDTO primary = getPerson(m.getPersonDTOS(), 1);
        EmailDTO primaryEmail = emailRepository.getPrimaryEmail(primary);
        PhoneDTO primaryPhone = phoneRepository.getPhoneByPersonAndType(primary.getpId(),"C");


        PersonDTO secondary = getPerson(m.getPersonDTOS(), 2);

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
                .queryParam("positionCredit", getInvoiceItemValue(items, "Position Credit"))
                .queryParam("primaryMember[first]", primary.getFirstName())
                .queryParam("primaryMember[last]", primary.getLastName())
                .queryParam("primaryOccupation", primary.getOccupation());

        if(primaryEmail != null)
            builder.queryParam("primaryemail", primaryEmail.getEmail());
        if(primaryPhone != null)
            builder.queryParam("primaryPhone", primaryPhone.getPhone());


        String url = builder.toUriString();
        return url;
    }

//    .queryParam("", "")

    private PersonDTO getPerson(ArrayList<PersonDTO> people, int personType) {
        return people.stream()
                .filter(personDTO -> personDTO.getMemberType() == personType)
                .findFirst()
                .orElse(null); // Returns null if no match is found
    }

    private String getInvoiceItemValue(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return invoiceItemDTO.getValue();
    }

    private String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return String.valueOf(invoiceItemDTO.getQty());
    }
}
