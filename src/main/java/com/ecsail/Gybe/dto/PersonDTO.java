package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    @JsonProperty("pId")
    private int pId;
    @JsonProperty("msId")
    private int msId;
    @JsonProperty("memberType")
    private int memberType; // 1 == primary 2 == secondary 3 == dependant
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("business")
    private String business;
    @JsonProperty("birthday")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("oldMsid")
    private int oldMsid;
    @JsonProperty("phones")
    private ArrayList<PhoneDTO> phones = new ArrayList<>();
    @JsonProperty("email")
    private ArrayList<EmailDTO> email = new ArrayList<>();
    @JsonIgnore
    @JsonProperty("awards")
    private ArrayList<AwardDTO> awards = new ArrayList<>();
    @JsonIgnore
    @JsonProperty("officer")
    private ArrayList<OfficerDTO> officer = new ArrayList<>();

    public PersonDTO() {
        super();
    }

    public PersonDTO(int pId,
                     int msId,
                     int memberType,
                     String firstName,
                     String lastName,
                     String occupation,
                     String business,
                     LocalDate birthday,
                     boolean active,
                     String nickName,
                     int oldMsid,
                     List<PhoneDTO> phones,
                     List<EmailDTO> email,
                     List<AwardDTO> awards,
                     List<OfficerDTO> officer) {
        this.pId = pId;
        this.msId = msId;
        this.memberType = memberType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.business = business;
        this.birthday = birthday;
        this.active = active;
        this.nickName = nickName;
        this.oldMsid = oldMsid;
        this.phones = phones != null ? (ArrayList<PhoneDTO>) phones : new ArrayList<>();
        this.email = email != null ? (ArrayList<EmailDTO>) email : new ArrayList<>();
        this.awards = awards != null ? (ArrayList<AwardDTO>) awards : new ArrayList<>();
        this.officer = officer != null ? (ArrayList<OfficerDTO>) officer : new ArrayList<>();
    }

    public PersonDTO(int pId,
                     int msId,
                     int memberType,
                     String firstName,
                     String lastName,
                     String occupation,
                     String business,
                     LocalDate birthday,
                     boolean active,
                     String nickName,
                     int oldMsid) {
        this.pId = pId;
        this.msId = msId;
        this.memberType = memberType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.business = business;
        this.birthday = birthday;
        this.active = active;
        this.nickName = nickName;
        this.oldMsid = oldMsid;
    }

    public PersonDTO(String firstName) {
        this.pId = 0;
        this.msId = 0;
        this.memberType = 0;
        this.firstName = firstName;
        this.lastName = "";
        this.occupation = "";
        this.business = "";
        this.active = true;
        this.nickName = "";
        this.oldMsid = 0;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getReversedFullName() {
        return lastName + ", " + firstName;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOldMsid() {
        return oldMsid;
    }

    public void setOldMsid(int oldMsid) {
        this.oldMsid = oldMsid;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = (ArrayList<PhoneDTO>) phones;
    }

    public List<EmailDTO> getEmails() {
        return email;
    }

    public void setEmails(List<EmailDTO> email) {
        this.email = (ArrayList<EmailDTO>) email;
    }

    public List<AwardDTO> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardDTO> awards) {
        this.awards = (ArrayList<AwardDTO>) awards;
    }

    public List<OfficerDTO> getOfficers() {
        return officer;
    }

    public void setOfficers(List<OfficerDTO> officer) {
        this.officer = (ArrayList<OfficerDTO>) officer;
    }

    @Override
    public String toString() {
        StringBuilder phonesStr = new StringBuilder();
        if (phones != null) {
            for (PhoneDTO phone : phones) {
                phonesStr.append("\t\t").append(phone).append("\n");
            }
        } else {
            phonesStr.append("\t\tphones: null\n");
        }

        StringBuilder emailStr = new StringBuilder();
        if (email != null) {
            for (EmailDTO email : email) {
                emailStr.append("\t\t").append(email).append("\n");
            }
        } else {
            emailStr.append("\t\temail: null\n");
        }

        StringBuilder awardsStr = new StringBuilder();
        if (awards != null) {
            for (AwardDTO award : awards) {
                awardsStr.append("\t\t").append(award).append("\n");
            }
        } else {
            awardsStr.append("\t\tawards: null\n");
        }

        StringBuilder officerStr = new StringBuilder();
        if (officer != null) {
            for (OfficerDTO officer : officer) {
                officerStr.append("\t\t").append(officer).append("\n");
            }
        } else {
            officerStr.append("\t\tofficer: null\n");
        }

        return "PersonDTO{" +
                "pId=" + pId +
                ", msId=" + msId +
                ", memberType=" + memberType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", business='" + business + '\'' +
                ", birthday=" + birthday +
                ", active=" + active +
                ", nickName='" + nickName + '\'' +
                ", oldMsid=" + oldMsid + "\n" +
                phonesStr + "\n" +
                emailStr +
                awardsStr +
                officerStr + "}";
    }
}
