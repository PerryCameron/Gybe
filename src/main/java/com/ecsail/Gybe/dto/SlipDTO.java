package com.ecsail.Gybe.dto;

public class SlipDTO {
    private Integer slip_id;
    private Integer ms_id;
    private String slip_num;
    private Integer subleased_to;
    private String alt_text;

    public SlipDTO() {
    }

    public SlipDTO(Integer slip_id, Integer ms_id, String slip_num, Integer subleased_to, String alt_text) {
        this.slip_id = slip_id;
        this.ms_id = ms_id;
        this.slip_num = slip_num;
        this.subleased_to = subleased_to;
        this.alt_text = alt_text;
    }

    public Integer getSlip_id() {
        return slip_id;
    }

    public void setSlip_id(Integer slip_id) {
        this.slip_id = slip_id;
    }

    public Integer getMs_id() {
        return ms_id;
    }

    public void setMs_id(Integer ms_id) {
        this.ms_id = ms_id;
    }

    public String getSlip_num() {
        return slip_num;
    }

    public void setSlip_num(String slip_num) {
        this.slip_num = slip_num;
    }

    public Integer getSubleased_to() {
        return subleased_to;
    }

    public void setSubleased_to(Integer subleased_to) {
        this.subleased_to = subleased_to;
    }

    public String getAlt_text() {
        return alt_text;
    }

    public void setAlt_text(String alt_text) {
        this.alt_text = alt_text;
    }

    @Override
    public String toString() {
        return "SlipDTO{" +
                "slip_id=" + slip_id +
                ", ms_id=" + ms_id +
                ", slip_num='" + slip_num + '\'' +
                ", subleased_to=" + subleased_to +
                ", alt_text='" + alt_text + '\'' +
                '}';
    }
}
