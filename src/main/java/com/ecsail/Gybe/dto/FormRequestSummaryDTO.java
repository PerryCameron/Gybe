package com.ecsail.Gybe.dto;

import java.sql.Timestamp;

public class FormRequestSummaryDTO {

    private int membershipId;
    private Timestamp newestHashReqDate;
    private String priMem;
    private String link;
    private String mailedTo;
    private int numHashDuplicates;
    private Timestamp newestFormReqDate;
    private int numFormAttempts;

    public FormRequestSummaryDTO(int membershipId, Timestamp newestHashReqDate, String priMem, String link, String mailedTo, int numHashDuplicates, Timestamp newestFormReqDate, int numFormAttempts) {
        this.membershipId = membershipId;
        this.newestHashReqDate = newestHashReqDate;
        this.priMem = priMem;
        this.link = link;
        this.mailedTo = mailedTo;
        this.numHashDuplicates = numHashDuplicates;
        this.newestFormReqDate = newestFormReqDate;
        this.numFormAttempts = numFormAttempts;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public Timestamp getNewestHashReqDate() {
        return newestHashReqDate;
    }

    public void setNewestHashReqDate(Timestamp newestHashReqDate) {
        this.newestHashReqDate = newestHashReqDate;
    }

    public String getPriMem() {
        return priMem;
    }

    public void setPriMem(String priMem) {
        this.priMem = priMem;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMailedTo() {
        return mailedTo;
    }

    public void setMailedTo(String mailedTo) {
        this.mailedTo = mailedTo;
    }

    public int getNumHashDuplicates() {
        return numHashDuplicates;
    }

    public void setNumHashDuplicates(int numHashDuplicates) {
        this.numHashDuplicates = numHashDuplicates;
    }

    public Timestamp getNewestFormReqDate() {
        return newestFormReqDate;
    }

    public void setNewestFormReqDate(Timestamp newestFormReqDate) {
        this.newestFormReqDate = newestFormReqDate;
    }

    public int getNumFormAttempts() {
        return numFormAttempts;
    }

    public void setNumFormAttempts(int numFormAttempts) {
        this.numFormAttempts = numFormAttempts;
    }
}
