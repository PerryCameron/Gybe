package com.ecsail.Gybe.dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonDTO {
	private int pId;
	private int msId;
	private int memberType; // 1 == primary 2 == secondary 3 == dependant
	private String firstName;
	private String lastName;
	private String occupation;
	private String business;
	private LocalDate birthday;
	private boolean active;
	private String nickName;
	private int oldMsid;
	private ArrayList<PhoneDTO> phones = new ArrayList<>();
	private ArrayList<EmailDTO> email = new ArrayList<>();
	private ArrayList<AwardDTO> awards = new ArrayList<>();
	private ArrayList<OfficerDTO> officer = new ArrayList<>();


	public PersonDTO(int pId, int msId, int memberType, String firstName, String lastName, String occupation, String business, LocalDate birthday, boolean active, String nickName, int oldMsid) {
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

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
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

	public ArrayList<PhoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(ArrayList<PhoneDTO> phones) {
		this.phones = phones;
	}

	public ArrayList<EmailDTO> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<EmailDTO> email) {
		this.email = email;
	}

	public ArrayList<AwardDTO> getAwards() {
		return awards;
	}

	public void setAwards(ArrayList<AwardDTO> awards) {
		this.awards = awards;
	}

	public ArrayList<OfficerDTO> getOfficer() {
		return officer;
	}

	public void setOfficer(ArrayList<OfficerDTO> officer) {
		this.officer = officer;
	}

	@Override
	public String toString() {
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
				", oldMsid=" + oldMsid +
				", phones=" + phones +
				", email=" + email +
				", awards=" + awards +
				", officer=" + officer +
				'}';
	}
}
