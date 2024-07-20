package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SlipInfoDTO {
    int ownerId;
    String ownerFirstName;
    String ownerLastName;
    int ownerMsid;
    String slipNumber;
    String altText;
    int subleaserId;
    int subleaserMsid;
    String subleaserFirstName;
    String subleaserLastName;

    public SlipInfoDTO(int ownerId, String ownerFirstName, String ownerLastName, int ownerMsid, String slipNumber, String altText, int subleaserId, int subleaserMsid, String subleaserFirstName, String subleaserLastName) {
        this.ownerId = ownerId;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerMsid = ownerMsid;
        this.slipNumber = slipNumber;
        this.altText = altText;
        this.subleaserId = subleaserId;
        this.subleaserMsid = subleaserMsid;
        this.subleaserFirstName = subleaserFirstName;
        this.subleaserLastName = subleaserLastName;
    }
    // JsonIgnore for following methods because they are used to make the directory not the slip chart
    @JsonIgnore
    public String getRightSlipOwner() {
        if (ownerFirstName.equals(""))
            return slipNumber + " " + ownerLastName;
        else
            return slipNumber + " " + ownerLastName + ", " + ownerFirstName;
    }
    @JsonIgnore
    public String getLeftSlipOwner() {
        if (ownerFirstName.equals(""))
            return ownerLastName + " " + slipNumber;
        else
            return ownerLastName + ", " + ownerFirstName + " " + slipNumber;
    }
    @JsonIgnore
    public String getRightSlipLeaser() {
        return slipNumber + " " + subleaserLastName + ", " + subleaserFirstName;
    }
    @JsonIgnore
    public String getLeftSlipLeaser() {
        return subleaserLastName + ", " + subleaserFirstName + " " + slipNumber;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public int getOwnerMsid() {
        return ownerMsid;
    }

    public String getSlipNumber() {
        return slipNumber;
    }

    public void setSlipNumber(String slipNumber) {
        this.slipNumber = slipNumber;
    }

    public int getSubleaserMsid() {
        return subleaserMsid;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerMsid(int ownerMsid) {
        this.ownerMsid = ownerMsid;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public int getSubleaserId() {
        return subleaserId;
    }

    public void setSubleaserId(int subleaserId) {
        this.subleaserId = subleaserId;
    }

    public void setSubleaserMsid(int subleaserMsid) {
        this.subleaserMsid = subleaserMsid;
    }

    public String getSubleaserFirstName() {
        return subleaserFirstName;
    }

    public void setSubleaserFirstName(String subleaserFirstName) {
        this.subleaserFirstName = subleaserFirstName;
    }

    public String getSubleaserLastName() {
        return subleaserLastName;
    }

    public void setSubleaserLastName(String subleaserLastName) {
        this.subleaserLastName = subleaserLastName;
    }
}

// /Users/parrishcameron/Documents/Perrys Projects/Gybe/src/main/java/com/ecsail/Gybe/dto/SlipInfoDTO.java

// git diff 247e5a981c7f8deea210df5d92ca7238178d0d2e  054b07507eb18843a75700d9d656e244011013e9 -- /Users/parrishcameron/Documents/Perrys Projects/Gybe/src/main/java/com/ecsail/Gybe/dto/SlipInfoDTO.java