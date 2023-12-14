package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.models.FormRequestModel;
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
    private final FormRequestModel model;
    private static final Logger logger = LoggerFactory.getLogger(FormRequestService.class);


    @Autowired
    public FormRequestService(
            HashRepositoryImpl hashRepository,
            MembershipRepositoryImpl membershipRepository,
            InvoiceRepositoryImpl invoiceRepository,
            PersonRepositoryImpl personRepository,
            EmailRepositoryImpl emailRepository,
            PhoneRepositoryImpl phoneRepository) {
        this.hashRepository = hashRepository;
        this.membershipRepository = membershipRepository;
        this.invoiceRepository = invoiceRepository;
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
        this.model = new FormRequestModel();
    }

    public String openForm(String hash) {
        // collect and organize data
        populateModel(hash);
        // use data to build and return link
        return buildLinkWithParameters();
    }
    public void populateModel(String hash) {
        model.setFormSettingsDTO(hashRepository.getFormSettings());
        model.setHashDTO(hashRepository.getHashDTOFromHash(Long.parseLong(hash)));
        // get the membership
        model.setMembershipListDTO(membershipRepository.getMembershipListFromMsidAndYear(model.getYear(), model.getMsId()));
        logger.info("Serving form to membership " + model.getMembershipId() + " " + model.getPrimaryFullName());
        // get the invoices
        model.setInvoiceDTOS((ArrayList<InvoiceDTO>) invoiceRepository.getInvoicesByMsidAndYear(model.getMsId(), model.getYear()));
        model.setInvoiceId(model.getInvoiceDTOS().get(0).getId());
        model.setInvoiceItemDTOS((ArrayList<InvoiceItemDTO>) invoiceRepository.getInvoiceItemsByInvoiceId(model.getInvoiceId()));
        // get the people
        model.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(model.getMsId()));
        // primary
        model.setPrimary(getPerson(model.getPersonDTOS(), 1));
        model.setPrimaryEmail(emailRepository.getPrimaryEmail(model.getPrimary()));
        model.setPrimaryCellPhone(phoneRepository.getPhoneByPersonAndType(model.getPrimary().getpId(),"C"));
        model.setPrimaryEmergencyPhone(phoneRepository.getPhoneByPersonAndType(model.getPrimary().getpId(),"E"));
        // secondary
        model.setSecondary(getPerson(model.getPersonDTOS(), 2));
        model.setSecondaryEmail(emailRepository.getPrimaryEmail(model.getSecondary()));
        model.setSecondaryCellPhone(phoneRepository.getPhoneByPersonAndType(model.getSecondary().getpId(),"C"));
    }

    // IMPLEMENT
    public String buildLinkWithParameters() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(model.getFormSettingsDTO().getForm_url())
                .path(model.getFormSettingsDTO().getForm_id())
                .queryParam("memId", model.getMembershipId())
                .queryParam("membershipType", model.getMembership().getMemType())
                .queryParam("address[addr_line1]", model.getMembership().getAddress())
                .queryParam("address[city]", model.getMembership().getCity())
                .queryParam("address[state]", model.getMembership().getState())
                .queryParam("address[postal]", model.getMembership().getZip())
                .queryParam("workCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Work Credits"))
                .queryParam("winterStorage", "winterStorage", getInvoiceItemQty(model.getInvoiceItemDTOS(), "Winter Storage"))
                .queryParam("additionalCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Other Credit"))
                .queryParam("otherFee", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Other Fee"))
                .queryParam("initiation", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Initiation"))
                .queryParam("positionCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Position Credit"))
                .queryParam("primaryMember[first]", model.getPrimary().getFirstName())
                .queryParam("primaryMember[last]", model.getPrimary().getLastName())
                .queryParam("primaryOccupation", model.getPrimary().getOccupation());

        if(model.getPrimaryEmail() != null)
            builder.queryParam("primaryEmail", model.getPrimaryEmail().getEmail());
        if(model.getPrimaryCellPhone() != null)
            builder.queryParam("primaryPhone", model.getPrimaryCellPhone().getPhone());
        if(model.getPrimaryEmergencyPhone() != null)
            builder.queryParam("emergencyPhone", model.getPrimaryEmergencyPhone().getPhone());
        if(model.getSecondary() != null) {
            builder.queryParam("haveSpouse", "Yes")
                    .queryParam("spouseName[first]",model.getSecondary().getFirstName())
                    .queryParam("spouseName[last]",model.getSecondary().getLastName())
                    .queryParam("spouseOccupation",model.getSecondary().getOccupation())
                    .queryParam("spouseCompany",model.getSecondary().getBusiness());
            if(model.getSecondaryCellPhone() != null)
                builder.queryParam("spousePhone", model.getSecondaryCellPhone().getPhone());
            if(model.getSecondaryEmail() != null)
                builder.queryParam("spouseEmail",model.getSecondaryEmail().getEmail());

        }
        return builder.toUriString();
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
