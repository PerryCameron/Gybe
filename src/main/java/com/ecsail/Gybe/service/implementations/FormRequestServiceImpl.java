package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.enums.MembershipType;
import com.ecsail.Gybe.models.FormRequestModel;
import com.ecsail.Gybe.repository.implementations.*;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.FormRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Service
public class FormRequestServiceImpl implements FormRequestService {
    private final HashRepository hashRepository;
    private final MembershipRepository membershipRepository;
    private final InvoiceRepository invoiceRepository;
    private final PersonRepository personRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final FormRequestModel model;
    private final SettingsServiceImpl settingService;
    private static final Logger logger = LoggerFactory.getLogger(FormRequestServiceImpl.class);
    private final SlipRepositoryImpl slipRepository;
    private final BoatRepositoryImpl boatRepository;


    @Autowired
    public FormRequestServiceImpl(
            HashRepositoryImpl hashRepository,
            MembershipRepositoryImpl membershipRepository,
            InvoiceRepositoryImpl invoiceRepository,
            PersonRepositoryImpl personRepository,
            EmailRepositoryImpl emailRepository,
            PhoneRepositoryImpl phoneRepository,
            SettingsServiceImpl settingsService,
            SlipRepositoryImpl slipRepository,
            BoatRepositoryImpl boatRepository) {
        this.hashRepository = hashRepository;
        this.membershipRepository = membershipRepository;
        this.invoiceRepository = invoiceRepository;
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
        this.settingService = settingsService;
        this.model = new FormRequestModel();
        this.slipRepository = slipRepository;
        this.boatRepository = boatRepository;
    }

    @Override
    public String openForm(String hash) {
        // collect and organize data
        populateModel(hash);
        // use data to build and return link
        return buildLinkWithParameters();
    }

    @Override
    public void populateModel(String hash) {
        model.setHashDTO(hashRepository.getHashDTOFromHash(Long.parseLong(hash)));
        // get the membership
        model.setMembershipListDTO(membershipRepository.getMembershipListFromMsidAndYear(
                settingService.getSelectedYear(), model.getMsId()));
        logger.info("Serving form to membership " + model.getMembershipId() + " " + model.getPrimaryFullName());
        // get the invoices
        model.setInvoiceDTOS((ArrayList<InvoiceDTO>) invoiceRepository.getInvoicesByMsidAndYear(model.getMsId(),
                settingService.getSelectedYear()));
        model.setInvoiceId(model.getInvoiceDTOS().get(0).getId());
        model.setInvoiceItemDTOS((ArrayList<InvoiceItemDTO>) invoiceRepository.getInvoiceItemsByInvoiceId(model.getInvoiceId()));
        // get slip
        model.setSlip(slipRepository.getSlip(model.getMsId()));
        // get the people
        model.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(model.getMsId()));
        // primary
        model.setPrimary(getPerson(model.getPersonDTOS(), 1));
        model.setPrimaryEmail(emailRepository.getPrimaryEmail(model.getPrimary()));
        model.setPrimaryCellPhone(phoneRepository.getPhoneByPersonAndType(model.getPrimary().getpId(), "C"));
        model.setPrimaryEmergencyPhone(phoneRepository.getPhoneByPersonAndType(model.getPrimary().getpId(), "E"));

        // secondary
        model.setSecondary(getPerson(model.getPersonDTOS(), 2));
        if (model.getSecondary() != null) {
            model.setSecondaryEmail(emailRepository.getPrimaryEmail(model.getSecondary()));
            model.setSecondaryCellPhone(phoneRepository.getPhoneByPersonAndType(model.getSecondary().getpId(), "C"));
        }
        // separate our dependents if they exist
        model.setDependents(model.extractDependentsFromPeople());
        // get our boats
        model.setBoatDTOS((ArrayList<BoatDTO>) boatRepository.getBoatsByMsId(model.getMsId()));
    }
// addDependent2
    @Override
    public String buildLinkWithParameters() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(settingService.getFormURL().getValue())
                .path(settingService.getFormId().getValue())
                .queryParam("slipNumber", model.getSlip().getSlip_num())
                .queryParam("numberOfDependents", model.getNumberOfDependents())
                .queryParam("addDependent2", determineIfAdd(2))
                .queryParam("addDependent3", determineIfAdd(3))
                .queryParam("addDependent4", determineIfAdd(4))
                .queryParam("addDependent5", determineIfAdd(5))
                .queryParam("addDependent6", determineIfAdd(6))
                .queryParam("addDependent7", determineIfAdd(7))
                .queryParam("memId", model.getMembershipId())
                .queryParam("membershipType", MembershipType.getByCode(model.getMembership().getMemType()))
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
                .queryParam("primaryOccupation", model.getPrimary().getOccupation())
                .queryParam("numberOfBoats", model.getBoatDTOS().size());

        if (model.getPrimaryEmail() != null)
            builder.queryParam("primaryEmail", model.getPrimaryEmail().getEmail());
        if (model.getPrimaryCellPhone() != null)
            builder.queryParam("primaryPhone", model.getPrimaryCellPhone().getPhone());
        if (model.getPrimaryEmergencyPhone() != null)
            builder.queryParam("emergencyPhone", model.getPrimaryEmergencyPhone().getPhone());
        if (model.getSecondary() != null) {
            builder.queryParam("haveSpouse", "Yes")
                    .queryParam("spouseName[first]", model.getSecondary().getFirstName())
                    .queryParam("spouseName[last]", model.getSecondary().getLastName())
                    .queryParam("spouseOccupation", model.getSecondary().getOccupation())
                    .queryParam("spouseCompany", model.getSecondary().getBusiness());
            if (model.getSecondaryCellPhone() != null)
                builder.queryParam("spousePhone", model.getSecondaryCellPhone().getPhone());
            if (model.getSecondaryEmail() != null)
                builder.queryParam("spouseEmail", model.getSecondaryEmail().getEmail());
        } else
            builder.queryParam("haveSpouse", "No");
        if (model.membershipHasDependents()) {
            int count = 1;
            for (PersonDTO personDTO : model.getDependents()) {
                String birthDay[] = personDTO.getBirthday().toString().split("-");
                builder.queryParam("dependentName" + count + "[first]", personDTO.getFirstName())
                        .queryParam("dependentName" + count + "[last]", personDTO.getLastName())
                        .queryParam("dependentBirthday" + count + "[day]", birthDay[2])
                        .queryParam("dependentBirthday" + count + "[month]", birthDay[1])
                        .queryParam("dependentBirthday" + count + "[year]", birthDay[0]);
                count++;
            }
        }
        if (model.membershipHasBoats()) {
            int count = 1;
            for (BoatDTO boatDTO : model.getBoatDTOS()) {
                builder.queryParam("addBoat" + count, "Yes")
                        .queryParam("boat_id" + count, boatDTO.getBoat_id())
                        .queryParam("boatName" + count, boatDTO.getBoat_name())
                        .queryParam("registrationNumber" + count, boatDTO.getRegistration_num())
                        .queryParam("manufacturer" + count, boatDTO.getManufacturer())
                        .queryParam("manufactureYear" + count, boatDTO.getManufacture_year())
                        .queryParam("model" + count, boatDTO.getModel())
                        .queryParam("length" + count, boatDTO.getLength())
                        .queryParam("draft" + count, boatDTO.getDraft())
                        .queryParam("sail" + count, boatDTO.getSail_number())
                        .queryParam("keelType" + count, toKeelType(boatDTO.getKeel()))
                        .queryParam("hasTrailer" + count, toHumanBool(boatDTO.getHasTrailer()));
                count++;
            }
        }
        hashRepository.insertHashHistory(new FormRequestDTO(model.getPrimaryFullName(), model.getMsId(), true));
        logger.info(builder.toUriString());
        return builder.toUriString();
    }

    private String determineIfAdd(int dependant) {
        int totalDependents = model.getNumberOfDependents();
        // Check if there are more than 1 dependents and dependant is less than totalDependents
        if (totalDependents > 1 && (dependant - 1) < totalDependents) {
            return "Yes";
        } else {
            return "No";
        }
    }

    private String toKeelType(String type) {
        String reducedType = "Other";
        switch (type) {
            case "FI","WI","FU","BU" -> reducedType = "Fixed";
            case "SW","CE","DA","RE" -> reducedType = "Retractable";
        }
        return reducedType;
    }

    private String toHumanBool(boolean booleanTest) {
        String answer = "Yes";
        if(booleanTest == false) answer = "No";
        return answer;
    }

    @Override
    public PersonDTO getPerson(ArrayList<PersonDTO> people, int personType) {
        return people.stream()
                .filter(personDTO -> personDTO.getMemberType() == personType)
                .findFirst()
                .orElse(null); // Returns null if no match is found
    }

    @Override
    public String getInvoiceItemValue(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return invoiceItemDTO.getValue();
    }

    @Override
    public String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return String.valueOf(invoiceItemDTO.getQty());
    }
}
