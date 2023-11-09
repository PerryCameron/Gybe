package com.ecsail.Gybe.dto;

public class EmailDTO {

	private Integer email_id;
	private Integer p_id;
	private Boolean primary_use;
	private String email;
	private Boolean email_listed;

	public EmailDTO(Integer email_id, Integer p_id, Boolean primary_use, String email, Boolean email_listed) {
		this.email_id = email_id;
		this.p_id = p_id;
		this.primary_use = primary_use;
		this.email = email;
		this.email_listed = email_listed;
	}

	public EmailDTO() {
	}

	public Integer getEmail_id() {
		return email_id;
	}

	public void setEmail_id(Integer email_id) {
		this.email_id = email_id;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
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

	public Boolean getEmail_listed() {
		return email_listed;
	}

	public void setEmail_listed(Boolean email_listed) {
		this.email_listed = email_listed;
	}

	@Override
	public String toString() {
		return "EmailDTO{" +
				"email_id=" + email_id +
				", p_id=" + p_id +
				", primary_use=" + primary_use +
				", email='" + email + '\'' +
				", email_listed=" + email_listed +
				'}';
	}
}
