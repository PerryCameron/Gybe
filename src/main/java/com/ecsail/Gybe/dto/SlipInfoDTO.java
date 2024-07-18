package com.ecsail.Gybe.dto;

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

    public String getRightSlipOwner() {
        if (ownerFirstName.equals(""))
            return slipNumber + " " + ownerLastName;
        else
            return slipNumber + " " + ownerLastName + ", " + ownerFirstName;
    }

    public String getLeftSlipOwner() {
        if (ownerFirstName.equals(""))
            return ownerLastName + " " + slipNumber;
        else
            return ownerLastName + ", " + ownerFirstName + " " + slipNumber;
    }

    public String getRightSlipLeaser() {
        return slipNumber + " " + subleaserLastName + ", " + subleaserFirstName;
    }

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
}

