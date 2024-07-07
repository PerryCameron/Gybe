package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MembershipInfoDTO {
    @JsonProperty("mid")
    private int mid;

    @JsonProperty("fiscalYear")
    private int fiscalYear;

    @JsonProperty("msId")
    private int msId;

    @JsonProperty("membershipId")
    private int membershipId;

    @JsonProperty("renew")
    private boolean renew;

    @JsonProperty("memType")
    private String memType;

    @JsonProperty("selected")
    private boolean selected;

    @JsonProperty("lateRenew")
    private boolean lateRenew;

    @JsonProperty("pId")
    private int pId;

    @JsonProperty("joinDate")
    private String joinDate;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    private String zip;
    @JsonIgnore
    @JsonProperty("slip")
    private SlipDTO slipDTO;
    @JsonIgnore
    @JsonProperty("people")
     private List<PersonDTO> people;
    @JsonIgnore
    @JsonProperty("boats")
    private List<BoatDTO> boats;



    // Getters and setters

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public boolean isRenew() {
        return renew;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isLateRenew() {
        return lateRenew;
    }

    public void setLateRenew(boolean lateRenew) {
        this.lateRenew = lateRenew;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public SlipDTO getSlip() {
        return slipDTO;
    }

    public void setSlip(SlipDTO slipDTO) {
        this.slipDTO = slipDTO;
    }

    // Uncomment and use if needed
    // public List<Person> getPersons() {
    //     return persons;
    // }

    // public void setPersons(List<Person> persons) {
    //     this.persons = persons;
    // }

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(List<BoatDTO> boats) {
        this.boats = boats;
    }

    @Override
    public String toString() {
        return "MembershipInfoDTO{" +
                "mid=" + mid +
                ", fiscalYear=" + fiscalYear +
                ", msId=" + msId +
                ", membershipId=" + membershipId +
                ", renew=" + renew +
                ", memType='" + memType + '\'' +
                ", selected=" + selected +
                ", lateRenew=" + lateRenew +
                ", pId=" + pId +
                ", joinDate='" + joinDate + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", slipDTO=" + slipDTO +
                ", people=" + people +
                ", boats=" + boats +
                '}';
    }
}
