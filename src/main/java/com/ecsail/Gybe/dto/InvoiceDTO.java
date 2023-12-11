package com.ecsail.Gybe.dto;


import java.util.ArrayList;

public class InvoiceDTO {
    private Integer id;
    private Integer msId;
    private Integer year;
    private String paid;
    private String total;
    private String credit;
    private String balance;
    private Integer batch;
    private Boolean committed;
    private Boolean closed;
    private Boolean supplemental;
    private String maxCredit;
    private ArrayList<InvoiceItemDTO> invoiceItems;


    public InvoiceDTO(Integer id, Integer msId, Integer year, String paid, String total, String credit, String balance, Integer batch, Boolean committed, Boolean closed, Boolean supplemental, String maxCredit) {
        this.id = id;
        this.msId = msId;
        this.year = year;
        this.paid = paid;
        this.total = total;
        this.credit = credit;
        this.balance = balance;
        this.batch = batch;
        this.committed = committed;
        this.closed = closed;
        this.supplemental = supplemental;
        this.maxCredit = maxCredit;
    }

    public ArrayList<InvoiceItemDTO> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(ArrayList<InvoiceItemDTO> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMsId() {
        return msId;
    }

    public Integer getYear() {
        return year;
    }

    public String getPaid() {
        return paid;
    }

    public String getTotal() {
        return total;
    }

    public String getCredit() {
        return credit;
    }

    public String getBalance() {
        return balance;
    }

    public Integer getBatch() {
        return batch;
    }

    public Boolean getCommitted() {
        return committed;
    }

    public Boolean getClosed() {
        return closed;
    }

    public Boolean getSupplemental() {
        return supplemental;
    }

    public String getMaxCredit() {
        return maxCredit;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMsId(Integer msId) {
        this.msId = msId;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public void setCommitted(Boolean committed) {
        this.committed = committed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public void setSupplemental(Boolean supplemental) {
        this.supplemental = supplemental;
    }

    public void setMaxCredit(String maxCredit) {
        this.maxCredit = maxCredit;
    }
}
