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
    int order;
    int boardYear;

    public LeadershipDTO(int oId, int pId, String firstName, String lastName, String position, boolean isOfficer, boolean isChair, boolean isAssistantChar, int order, int boardYear) {
        this.oId = oId;
        this.pId = pId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.isOfficer = isOfficer;
        this.isChair = isChair;
        this.isAssistantChar = isAssistantChar;
        this.order = order;
        this.boardYear = boardYear;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getBoardYear() {
        return boardYear;
    }

    public void setBoardYear(int boardYear) {
        this.boardYear = boardYear;
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
                ", order=" + order +
                ", boardYear=" + boardYear +
                '}';
    }
}
