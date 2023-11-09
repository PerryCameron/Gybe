package com.ecsail.Gybe.dto;



public class MembershipDTO {

	private Integer ms_id; /// unique auto key for Membership
	private Integer p_id;  /// pid of Main Member
	private String join_date;
	private String mem_type;  // Type of Membership (Family, Regular, Lake Associate(race fellow), Social
	private String address;
	private String city;
	private String state;
	private String zip;

	public MembershipDTO() {
	}

	public MembershipDTO(Integer ms_id, Integer p_id, String join_date, String mem_type, String address, String city, String state, String zip) {
		this.ms_id = ms_id;
		this.p_id = p_id;
		this.join_date = join_date;
		this.mem_type = mem_type;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public Integer getMs_id() {
		return ms_id;
	}

	public void setMs_id(Integer ms_id) {
		this.ms_id = ms_id;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public String getMem_type() {
		return mem_type;
	}

	public void setMem_type(String mem_type) {
		this.mem_type = mem_type;
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
				"msid=" + ms_id +
				", pid=" + p_id +
				", joinDate='" + join_date + '\'' +
				", memType='" + mem_type + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				'}';
	}
}
