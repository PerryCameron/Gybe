package com.ecsail.Gybe.dto;
/// this class is for racing and 48-hour docks
public class SlipAltDTO {
    String slip;
    String designation;

    public SlipAltDTO(String slip, String designation) {
        this.slip = slip;
        this.designation = designation;
    }

    public void setInfoDTO(SlipInfoDTO info) {
        info.setSlipNumber(slip);
        info.setOwnerLastName(designation);
        info.setOwnerFirstName("");
    }

    public String getSlip() {
        return slip;
    }

    public void setSlip(String slip) {
        this.slip = slip;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
