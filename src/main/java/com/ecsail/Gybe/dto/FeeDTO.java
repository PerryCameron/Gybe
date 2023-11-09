package com.ecsail.Gybe.dto;



public class FeeDTO {
    private int feeId;
    private String fieldName;
    private String fieldValue;
    private int dbInvoiceID;
    private int feeYear;
    private String description;
    private Boolean defaultFee;

    public FeeDTO(int feeId, String fieldName, String fieldValue, int dbInvoiceID, int feeYear, String description, Boolean defaultFee) {
        this.feeId = feeId;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.dbInvoiceID = dbInvoiceID;
        this.feeYear = feeYear;
        this.description = description;
        this.defaultFee = defaultFee;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getDbInvoiceID() {
        return dbInvoiceID;
    }

    public void setDbInvoiceID(int dbInvoiceID) {
        this.dbInvoiceID = dbInvoiceID;
    }

    public int getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDefaultFee() {
        return defaultFee;
    }

    public void setDefaultFee(Boolean defaultFee) {
        this.defaultFee = defaultFee;
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
                "feeId=" + feeId +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", dbInvoiceID=" + dbInvoiceID +
                ", feeYear=" + feeYear +
                ", description='" + description + '\'' +
                ", defaultFee=" + defaultFee +
                '}';
    }
}
