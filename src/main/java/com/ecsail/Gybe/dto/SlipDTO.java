package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlipDTO {
    @JsonProperty("slipId")
    private Integer slipId;

    @JsonProperty("msId")
    private Integer msId;

    @JsonProperty("slipNum")
    private String slipNum;

    @JsonProperty("subleasedTo")
    private Integer subleasedTo;

    @JsonProperty("altText")
    private String altText;

    public SlipDTO() {
        super();
    }

    public SlipDTO(Integer slipId, Integer msId, String slipNum, Integer subleasedTo, String altText) {
        this.slipId = slipId;
        this.msId = msId;
        this.slipNum = slipNum;
        this.subleasedTo = subleasedTo;
        this.altText = altText;
    }

    public Integer getSlipId() {
        return slipId;
    }

    public void setSlipId(Integer slipId) {
        this.slipId = slipId;
    }

    public Integer getMsId() {
        return msId;
    }

    public void setMsId(Integer msId) {
        this.msId = msId;
    }

    public String getSlipNum() {
        return slipNum;
    }

    public void setSlipNum(String slipNum) {
        this.slipNum = slipNum;
    }

    public Integer getSubleasedTo() {
        return subleasedTo;
    }

    public void setSubleasedTo(Integer subleasedTo) {
        this.subleasedTo = subleasedTo;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public String toString() {
        return "\n\tSlipDTO{" +
                "slipId=" + slipId +
                ", msId=" + msId +
                ", slipNum='" + slipNum + '\'' +
                ", subleasedTo=" + subleasedTo +
                ", altText='" + altText + '\'' +
                '}';
    }
}
