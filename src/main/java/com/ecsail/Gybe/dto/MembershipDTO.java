package com.ecsail.Gybe.dto;



public class MembershipDTO {

	private Integer msId; /// unique auto key for Membership
	private Integer pId;  /// pid of Main Member
	private String joinDate;
	private String memType;  // Type of Membership (Family, Regular, Lake Associate(race fellow), Social
	private String address;
	private String city;
	private String state;
	private String zip;

	public MembershipDTO() {
	}

	public MembershipDTO(Integer msId, Integer pId, String joinDate, String memType, String address, String city, String state, String zip) {
		this.msId = msId;
		this.pId = pId;
		this.joinDate = joinDate;
		this.memType = memType;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public Integer getMsId() {
		return msId;
	}

	public void setMsId(Integer msId) {
		this.msId = msId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
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

	@Override
	public String toString() {
		return "Object_Membership{" +
				"msid=" + msId +
				", pid=" + pId +
				", joinDate='" + joinDate + '\'' +
				", memType='" + memType + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				'}';
	}
}
