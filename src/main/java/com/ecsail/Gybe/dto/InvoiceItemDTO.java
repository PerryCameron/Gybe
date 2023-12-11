package com.ecsail.Gybe.dto;

public class InvoiceItemDTO {
    int id;
    int invoiceId;
    int msId;
    int year;
    String fieldName;
    boolean credit;
    String value;
    int qty;
    boolean category;
    String categoryItem;

    public InvoiceItemDTO(int id, int invoiceId, int msId, int year, String fieldName, boolean credit, String value, int qty, boolean category, String categoryItem) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.msId = msId;
        this.year = year;
        this.fieldName = fieldName;
        this.credit = credit;
        this.value = value;
        this.qty = qty;
        this.category = category;
        this.categoryItem = categoryItem;
    }

    public InvoiceItemDTO(int id, int invoiceId, int msId, int year, String fieldName, boolean credit, String value, int qty) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.msId = msId;
        this.year = year;
        this.fieldName = fieldName;
        this.credit = credit;
        this.value = value;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public String getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(String categoryItem) {
        this.categoryItem = categoryItem;
    }

    @Override
    public String toString() {
        return "InvoiceItemDTO{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", msId=" + msId +
                ", year=" + year +
                ", fieldName='" + fieldName + '\'' +
                ", credit=" + credit +
                ", value='" + value + '\'' +
                ", qty=" + qty +
                ", category=" + category +
                ", categoryItem='" + categoryItem + '\'' +
                '}';
    }
}
