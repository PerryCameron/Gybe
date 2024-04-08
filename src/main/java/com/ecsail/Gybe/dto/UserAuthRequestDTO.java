package com.ecsail.Gybe.dto;

import java.time.LocalDate;

public class UserAuthRequestDTO {
    String passKey;
    int pId;
    LocalDate updatedAt;
    LocalDate completed;

    public UserAuthRequestDTO(String passKey, int pId, LocalDate updatedAt, LocalDate completed) {
        this.passKey = passKey;
        this.pId = pId;
        this.updatedAt = updatedAt;
        this.completed = completed;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCompleted() {
        return completed;
    }

    public void setCompleted(LocalDate completed) {
        this.completed = completed;
    }
}
