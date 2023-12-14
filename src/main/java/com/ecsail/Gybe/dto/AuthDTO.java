package com.ecsail.Gybe.dto;

public class AuthDTO {
	private Integer pId;
	private Integer msId;
	private Integer memberType; // 1 == primary 2 == secondary 3 == children of
	private String fName;
	private String lName;
	private String email;
	private String htmlPage;
	private Boolean exists;
	private String nickName;

	public AuthDTO() {
	}

	public AuthDTO(Integer pId, Integer msId, Integer memberType, String firstName, String lastName, String email, String htmlPage, Boolean exists, String nickName) {
		this.pId = pId;
		this.msId = msId;
		this.memberType = memberType;
		this.fName = firstName;
		this.lName = lastName;
		this.email = email;
		this.htmlPage = htmlPage;
		this.exists = exists;
		this.nickName = nickName;
	}

	public AuthDTO(Integer pId, Integer msId, Integer memberType, String firstName, String lastName, String email, String nickName) {
		this.pId = pId;
		this.msId = msId;
		this.memberType = memberType;
		this.fName = firstName;
		this.lName = lastName;
		this.email = email;
		this.nickName = nickName;
	}

	public void copyFrom(AuthDTO other) {
		if (other == null) {
			return; // or throw IllegalArgumentException
		}
		this.pId = other.getpId();
		this.msId = other.getMsId();
		this.memberType = other.getMemberType();
		this.fName = other.getfName();
		this.lName = other.getlName();
		this.email = other.getEmail();
		this.htmlPage = other.getHtmlPage();
		this.exists = other.getExists();
		this.nickName = other.getNickName();
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getMsId() {
		return msId;
	}

	public void setMsId(Integer msId) {
		this.msId = msId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	@Override
	public String toString() {
		return "AuthDTO{" +
				"p_id=" + pId +
				", ms_id=" + msId +
				", member_type=" + memberType +
				", f_name='" + fName + '\'' +
				", l_name='" + lName + '\'' +
				", email='" + email + '\'' +
				", htmlPage='" + htmlPage + '\'' +
				", exists=" + exists +
				", nick_name='" + nickName + '\'' +
				'}';
	}
}
