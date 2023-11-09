package com.ecsail.Gybe.dto;


import java.time.LocalDate;

public class NotesDTO {

    private int memoId;
    private int msId;
    private LocalDate memoDate;
    private String memo;
    private int invoiceId;
    private String category;
    private int boatId;

    public NotesDTO(int memoId, int msId, LocalDate memoDate, String memo, int invoiceId, String category, int boatId) {
        this.memoId = memoId;
        this.msId = msId;
        this.memoDate = memoDate;
        this.memo = memo;
        this.invoiceId = invoiceId;
        this.category = category;
        this.boatId = boatId;
    }

    public int getMemoId() {
        return memoId;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public LocalDate getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(LocalDate memoDate) {
        this.memoDate = memoDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    @Override
    public String toString() {
        return "NotesDTO{" +
                "memoId=" + memoId +
                ", msId=" + msId +
                ", memoDate=" + memoDate +
                ", memo='" + memo + '\'' +
                ", invoiceId=" + invoiceId +
                ", category='" + category + '\'' +
                ", boatId=" + boatId +
                '}';
    }
}
