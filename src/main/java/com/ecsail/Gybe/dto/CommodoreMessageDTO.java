package com.ecsail.Gybe.dto;

public class CommodoreMessageDTO {
    private int id;
    private int fiscalYear;
    private String salutation;
    private String message;
    private String commodore;
    private int pid;

    public CommodoreMessageDTO(int id, int fiscalYear, String salutation, String message, String commodore, int pid) {
        this.id = id;
        this.fiscalYear = fiscalYear;
        this.salutation = salutation;
        this.message = message;
        this.commodore = commodore;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommodore() {
        return commodore;
    }

    public void setCommodore(String commodore) {
        this.commodore = commodore;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
