package com.ecsail.Gybe.dto;


import java.util.ArrayList;

public class PhoneDTO {

	private int phoneId;
	private int pId;
	private boolean phoneListed;
	private String phone;
	private String phoneType;
	private ArrayList<String> membershipTypes;


	public PhoneDTO(int phoneId, int pId, boolean phoneListed, String phone, String phoneType) {
		this.phoneId = phoneId;
		this.pId = pId;
		this.phoneListed = phoneListed;
		this.phone = phone;
		this.phoneType = phoneType;
	}

	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public boolean isPhoneListed() {
		return phoneListed;
	}

	public void setPhoneListed(boolean phoneListed) {
		this.phoneListed = phoneListed;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public ArrayList<String> getMembershipTypes() {
		return membershipTypes;
	}

	public void setMembershipTypes(ArrayList<String> membershipTypes) {
		this.membershipTypes = membershipTypes;
	}

	@Override
	public String toString() {
		return "PhoneDTO{" +
				"phoneId=" + phoneId +
				", pId=" + pId +
				", phoneListed=" + phoneListed +
				", phone='" + phone + '\'' +
				", phoneType='" + phoneType + '\'' +
				", membershipTypes=" + membershipTypes +
				'}';
	}
}
