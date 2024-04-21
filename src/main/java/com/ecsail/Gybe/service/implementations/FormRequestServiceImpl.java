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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String link = null;
        // collect and organize data
        try {
            logger.info("populating model");
            populateModel(hash);
            logger.info("creating link");
            link = buildLinkWithParameters();
        } catch (Exception e) {
            logger.error("Could not populate Model: " + e);
        }
        // use data to build and return link
        logger.info("Returning link");
        return link;
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
                .path(settingService.getFormId().getValue());

        if(model.getSlip() != null)
            setParameter("sln", model.getSlip().getSlip_num(), builder);
        else logger.error("model.getSlip() is null");

        builder.queryParam("nod", model.getNumberOfDependents())
                .queryParam("d2", determineIfAdd(2))
                .queryParam("d3", determineIfAdd(3))
                .queryParam("d4", determineIfAdd(4))
                .queryParam("d5", determineIfAdd(5))
                .queryParam("d6", determineIfAdd(6))
                .queryParam("d7", determineIfAdd(7));


        builder.queryParam("memId", model.getMembershipId());

        if(model.getMembership() != null) {
            setParameter("met", String.valueOf(MembershipType.getByCode(model.getMembership().getMemType())), builder);
            setParameter("address[addr_line1]", model.getMembership().getAddress(), builder);
            setParameter("address[city]", model.getMembership().getCity(), builder);
            setParameter("address[state]", model.getMembership().getState(), builder);
            setParameter("address[postal]", model.getMembership().getZip(), builder);
        } else logger.error("model.getMembership() is null");

        if(model.getInvoiceItemDTOS() != null) {
            setParameter("workCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Work Credits"), builder);
            setParameter("winterStorage", getInvoiceItemQty(model.getInvoiceItemDTOS(), "Winter Storage"), builder);
            setParameter("additionalCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Other Credit"), builder);
            setParameter("otherFee", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Other Fee"), builder);
            setParameter("initiation", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Initiation"), builder);
            setParameter("positionCredit", getInvoiceItemValue(model.getInvoiceItemDTOS(), "Position Credit"), builder);
        } else logger.error("model.getInvoiceItemDTOS() is null");

        if(model.getPrimary() != null) {
            setParameter("primaryMember[first]", model.getPrimary().getFirstName(), builder);
            setParameter("primaryMember[last]", model.getPrimary().getLastName(), builder);
            setParameter("primaryOccupation", model.getPrimary().getOccupation(), builder);
        } else logger.error("model.getPrimary() is null");

        if(model.getBoatDTOS() != null)  // probably don't need this one
            setParameter("numberOfBoats", String.valueOf(model.getBoatDTOS().size()), builder);
        else logger.error("model.getBoatDTOS() is null");

        if(model.getPrimaryEmail() != null)
            setParameter("primaryEmail", model.getPrimaryEmail().getEmail(), builder);
        else logger.error("model.getPrimaryEmail() is null");

        if(model.getPrimaryCellPhone() != null)
            setParameter("primaryPhone", model.getPrimaryCellPhone().getPhone(), builder);
        else logger.error("model.getPrimaryCellPhone() is null");

        if(model.getPrimaryEmergencyPhone() != null)
            setParameter("emergencyPhone", model.getPrimaryEmergencyPhone().getPhone(), builder);
        else logger.error("model.getPrimaryEmergencyPhone() is null");

        if (model.getSecondary() != null) {
            builder.queryParam("haveSpouse", "Yes");
            setParameter("spouseName[first]", model.getSecondary().getFirstName(), builder);
            setParameter("spouseName[last]", model.getSecondary().getLastName(), builder);
            setParameter("spouseOccupation", model.getSecondary().getOccupation(), builder);
            setParameter("spouseCompany", model.getSecondary().getBusiness(), builder);
            if(model.getSecondaryCellPhone() != null)
                setParameter("spousePhone", model.getSecondaryCellPhone().getPhone(), builder);
            else logger.error("model.getSecondaryCellPhone() is null");

            if(model.getSecondaryEmail() != null)
                setParameter("spouseEmail", model.getSecondaryEmail().getEmail(), builder);
            else logger.error("model.getSecondaryEmail() is null");

        } else
            builder.queryParam("haveSpouse", "No");
        if (model.membershipHasDependents()) {
            int count = 1;
            for (PersonDTO personDTO : model.getDependents()) {
                String birthDay[] = personDTO.getBirthday().toString().split("-");
                setParameter("dependentName" + count + "[first]", personDTO.getFirstName(), builder);
                setParameter("dependentName" + count + "[last]", personDTO.getLastName(), builder);
                setParameter("dependentBirthday" + count + "[day]", birthDay[2], builder);
                setParameter("dependentBirthday" + count + "[month]", birthDay[1], builder);
                setParameter("dependentBirthday" + count + "[year]", birthDay[0], builder);
                count++;
            }
        }
        if (model.membershipHasBoats()) {
            int count = 1;
            for (BoatDTO boatDTO : model.getBoatDTOS()) {
                if(boatDTO.getKeel() != null)
                    setParameter("keelType" + count, toKeelType(boatDTO.getKeel()), builder);
                else logger.error("boatDTO.getKeel() is null");

                if(boatDTO.getBeam() != null)
                    setParameter("beam" + count, extractFirstNumber(boatDTO.getBeam()), builder);
                else logger.error("boatDTO.getBeam() is null");

                setParameter("addBoat" + count, "Yes", builder);

                if(boatDTO.getBoatId() != null)
                setParameter("boat_id" + count, String.valueOf(boatDTO.getBoatId()), builder);
                else logger.error("boatDTO.getBoat_id() is null");

                if(boatDTO.getBoatName() != null)
                setParameter("boatName" + count, boatDTO.getBoatName(), builder);
                else logger.error("boatDTO.getBoat_name() is null");

                if(boatDTO.getRegistrationNum() != null)
                setParameter("registrationNumber" + count, boatDTO.getRegistrationNum(), builder);
                else logger.error("boatDTO.getRegistration_num() is null");

                if(boatDTO.getManufacturer() != null)
                setParameter("manufacturer" + count, boatDTO.getManufacturer(), builder);
                else logger.error("boatDTO.getManufacturer() is null");

                if(boatDTO.getManufactureYear() != null)
                setParameter("manufactureYear" + count, boatDTO.getManufactureYear(), builder);
                else logger.error("boatDTO.getManufacture_year() is null");

                if(boatDTO.getModel() != null)
                setParameter("model" + count, boatDTO.getModel(), builder);
                else logger.error("boatDTO.getModel() is null");

                if(boatDTO.getLoa() != null)
                setParameter("length" + count, boatDTO.getLoa(), builder);
                else logger.error("boatDTO.getLength() is null");

                if(boatDTO.getDraft() != null)
                setParameter("draft" + count, boatDTO.getDraft(), builder);
                else logger.error("boatDTO.getDraft() is null");

                if(boatDTO.getSailNumber() != null)
                setParameter("sail" + count, boatDTO.getSailNumber(), builder);
                else logger.error("boatDTO.getSail_number() is null");

                if(boatDTO.getHasTrailer() != null)
                setParameter("hasTrailer" + count, toHumanBool(boatDTO.getHasTrailer()), builder);
                else logger.error("boatDTO.getHasTrailer() is null");

                count++;
            }
        }
        hashRepository.insertHashHistory(new FormRequestDTO(model.getPrimaryFullName(), model.getMsId(), true));
        logger.info(builder.toUriString());
        return builder.toUriString();
    }

    private void setParameter(String key, String value, UriComponentsBuilder builder) {
        if (value != null && !value.equals("")) {
            builder.queryParam(key, value);
        } else
            logger.info(key + " = \"\"");
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
            case "FI", "WI", "FU", "BU" -> reducedType = "Fixed";
            case "SW", "CE", "DA", "RE" -> reducedType = "Retractable";
        }
        return reducedType;
    }

    private String toHumanBool(boolean booleanTest) {
        String answer = "Yes";
        if (booleanTest == false) answer = "No";
        return answer;
    }

    public static String extractFirstNumber(String input) {
        // Regular expression to match a numeric value (including decimals)
        String regex = "\\d+\\.\\d+|\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Convert the first match to a double and return it
            return String.valueOf(Double.parseDouble(matcher.group()));
        } else {
            // Throw an exception or return a default value if no number is found
            return "";
        }
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
        logger.info(invoiceItemDTO.getFieldName() + " = " + invoiceItemDTO.getValue());
        return invoiceItemDTO.getValue();
    }

    @Override
    public String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
                .filter(item -> item.getFieldName().equals(fieldName)).findFirst().orElse(null);
        return String.valueOf(invoiceItemDTO.getQty());
    }
}
