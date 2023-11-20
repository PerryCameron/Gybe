package com.ecsail.Gybe.dto;

public class EmailDTO {

	private Integer emailId;
	private Integer pId;
	private Boolean primary_use;
	private String email;
	private Boolean emailListed;

	public EmailDTO(Integer emailId, Integer pId, Boolean primary_use, String email, Boolean emailListed) {
		this.emailId = emailId;
		this.pId = pId;
		this.primary_use = primary_use;
		this.email = email;
		this.emailListed = emailListed;
	}

	public EmailDTO() {
	}

	public Integer getEmailId() {
		return emailId;
	}

	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Boolean getPrimary_use() {
		return primary_use;
	}

	public void setPrimary_use(Boolean primary_use) {
		this.primary_use = primary_use;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailListed() {
		return emailListed;
	}

	public void setEmailListed(Boolean emailListed) {
		this.emailListed = emailListed;
	}

	@Override
	public String toString() {
		return "EmailDTO{" +
				"email_id=" + emailId +
				", p_id=" + pId +
				", primary_use=" + primary_use +
				", email='" + email + '\'' +
				", email_listed=" + emailListed +
				'}';
	}
}
