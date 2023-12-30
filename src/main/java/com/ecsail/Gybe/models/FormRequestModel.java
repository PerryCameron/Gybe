package com.ecsail.Gybe.models;

import com.ecsail.Gybe.dto.*;

import java.util.ArrayList;

public class FormRequestModel {

    SlipDTO slip;
    HashDTO hashDTO = null;
    MembershipListDTO membershipListDTO = new MembershipListDTO();
    ArrayList<InvoiceDTO> invoiceDTOS = membershipListDTO.getInvoiceDTOS();
    ArrayList<InvoiceItemDTO> invoiceItemDTOS = null;
    ArrayList<PersonDTO> personDTOS = membershipListDTO.getPersonDTOS();
    PersonDTO primary = null;
    PersonDTO secondary = null;
    ArrayList<PersonDTO> dependents = null;
    EmailDTO primaryEmail = null;
    PhoneDTO primaryCellPhone = null;
    PhoneDTO primaryEmergencyPhone = null;
    EmailDTO secondaryEmail = null;
    PhoneDTO secondaryCellPhone = null;
    int invoiceId = 0;


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
