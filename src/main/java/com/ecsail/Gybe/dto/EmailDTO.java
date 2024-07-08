package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailDTO {

	@JsonProperty("emailId")
	private Integer emailId;
	@JsonProperty("pId")
	private Integer pId;
	@JsonProperty("primaryUse")
	private Boolean primaryUse;
	@JsonProperty("email")
	private String email;
	@JsonProperty("emailListed")
	private Boolean emailListed;

	public EmailDTO(Integer emailId, Integer pId, Boolean primaryUse, String email, Boolean emailListed) {
		this.emailId = emailId;
		this.pId = pId;
		this.primaryUse = primaryUse;
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

	public Boolean getPrimaryUse() {
		return primaryUse;
	}

	public void setPrimaryUse(Boolean primaryUse) {
		this.primaryUse = primaryUse;
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
				"emailId=" + emailId +
				", pId=" + pId +
				", primaryUse=" + primaryUse +
				", email='" + email + '\'' +
				", emailListed=" + emailListed +
				'}';
	}

}
