package com.ecsail.Gybe.models;

import com.ecsail.Gybe.dto.*;

import java.util.ArrayList;

public class FormRequestModel {

    private SlipDTO slip;
    private HashDTO hashDTO = null;
    private MembershipListDTO membershipListDTO = new MembershipListDTO();
    private ArrayList<InvoiceDTO> invoiceDTOS = membershipListDTO.getInvoiceDTOS();
    private ArrayList<InvoiceItemDTO> invoiceItemDTOS = null;
    private ArrayList<PersonDTO> personDTOS = membershipListDTO.getPersonDTOS();
    private ArrayList<BoatDTO> boatDTOS = membershipListDTO.getBoatDTOS();
    private PersonDTO primary = null;
    private PersonDTO secondary = null;
    private ArrayList<PersonDTO> dependents = null;
    private EmailDTO primaryEmail = null;
    private PhoneDTO primaryCellPhone = null;
    private PhoneDTO primaryEmergencyPhone = null;
    private EmailDTO secondaryEmail = null;
    private PhoneDTO secondaryCellPhone = null;
    private int invoiceId = 0;


    public boolean membershipHasBoats() {
        if(boatDTOS.size() > 0) return true;
        else return false;
    }

    public boolean membershipHasDependents() {
        if(dependents.size() > 0) return true;
        else return false;
    }
    public ArrayList<BoatDTO> getBoatDTOS() {
        return boatDTOS;
    }

    public void setBoatDTOS(ArrayList<BoatDTO> boatDTOS) {
        this.boatDTOS = boatDTOS;
    }

    public ArrayList<PersonDTO> extractDependentsFromPeople() {
        ArrayList<PersonDTO> dependents = new ArrayList<>();
        personDTOS.stream().filter(personDTO -> personDTO.getMemberType() == 3).forEach(dependents::add);
        return dependents;
    }

    public int getNumberOfDependents() {
        if(dependents.isEmpty())
            return 0;
        else
            return dependents.size();
    }

    public SlipDTO getSlip() {
        return slip;
    }

    public void setSlip(SlipDTO slip) {
        this.slip = slip;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ArrayList<InvoiceItemDTO> getInvoiceItemDTOS() {
        return invoiceItemDTOS;
    }

    public void setInvoiceItemDTOS(ArrayList<InvoiceItemDTO> invoiceItemDTOS) {
        this.invoiceItemDTOS = invoiceItemDTOS;
    }

    public EmailDTO getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(EmailDTO primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public PhoneDTO getPrimaryCellPhone() {
        return primaryCellPhone;
    }

    public void setPrimaryCellPhone(PhoneDTO primaryCellPhone) {
        this.primaryCellPhone = primaryCellPhone;
    }

    public PhoneDTO getPrimaryEmergencyPhone() {
        return primaryEmergencyPhone;
    }

    public void setPrimaryEmergencyPhone(PhoneDTO primaryEmergencyPhone) {
        this.primaryEmergencyPhone = primaryEmergencyPhone;
    }

    public EmailDTO getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(EmailDTO secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public PhoneDTO getSecondaryCellPhone() {
        return secondaryCellPhone;
    }

    public void setSecondaryCellPhone(PhoneDTO secondaryCellPhone) {
        this.secondaryCellPhone = secondaryCellPhone;
    }

    public PersonDTO getPrimary() {
        return primary;
    }

    public void setPrimary(PersonDTO primary) {
        this.primary = primary;
    }

    public PersonDTO getSecondary() {
        return secondary;
    }

    public void setSecondary(PersonDTO secondary) {
        this.secondary = secondary;
    }

    public ArrayList<PersonDTO> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<PersonDTO> dependents) {
        this.dependents = dependents;
    }

    public ArrayList<PersonDTO> getPersonDTOS() {
        return personDTOS;
    }

    public void setPersonDTOS(ArrayList<PersonDTO> personDTOS) {
        this.personDTOS = personDTOS;
    }

    public MembershipListDTO getMembershipListDTO() {
        return membershipListDTO;
    }

    public ArrayList<InvoiceDTO> getInvoiceDTOS() {
        return invoiceDTOS;
    }

    public void setInvoiceDTOS(ArrayList<InvoiceDTO> invoiceDTOS) {
        this.invoiceDTOS = invoiceDTOS;
    }

    public String getPrimaryFullName() {
        return membershipListDTO.getFullName();
    }
    public int getMembershipId() {
        return membershipListDTO.getMembershipId();
    }
    public int getMsId() {
        return hashDTO.getMsId();
    }

    public HashDTO getHashDTO() {
        return hashDTO;
    }

    public void setHashDTO(HashDTO hashDTO) {
        this.hashDTO = hashDTO;
    }

    public MembershipListDTO getMembership() {
        return membershipListDTO;
    }

    public void setMembershipListDTO(MembershipListDTO membershipListDTO) {
        this.membershipListDTO = membershipListDTO;
    }

}
