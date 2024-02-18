package com.ecsail.Gybe.dto;

import java.util.Objects;

public class LeadershipDTO {
    int oId;
    int pId;
    String firstName;
    String lastName;
    String position;
    boolean isOfficer;
    boolean isChair;
    boolean isAssistantChar;

    public LeadershipDTO(int oId, int pId, String firstName, String lastName, String position, boolean isOfficer, boolean isChair, boolean isAssistantChar) {
        this.oId = oId;
        this.pId = pId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.isOfficer = isOfficer;
        this.isChair = isChair;
        this.isAssistantChar = isAssistantChar;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public void setOfficer(boolean officer) {
        isOfficer = officer;
    }

    public boolean isChair() {
        return isChair;
    }

    public void setChair(boolean chair) {
        isChair = chair;
    }

    public boolean isAssistantChar() {
        return isAssistantChar;
    }

    public void setAssistantChar(boolean assistantChar) {
        isAssistantChar = assistantChar;
    }

    @Override
    public String toString() {
        return "LeadershipDTO{" +
                "oId=" + oId +
                ", pId=" + pId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", isOfficer=" + isOfficer +
                ", isChair=" + isChair +
                ", isAssistantChar=" + isAssistantChar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeadershipDTO that = (LeadershipDTO) o;
        return oId == that.oId && pId == that.pId && isOfficer == that.isOfficer && isChair == that.isChair && isAssistantChar == that.isAssistantChar && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oId, pId, firstName, lastName, position, isOfficer, isChair, isAssistantChar);
    }
}
