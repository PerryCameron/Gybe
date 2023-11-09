package com.ecsail.Gybe.dto;

public class AuthDTO {
	private Integer p_id;
	private Integer ms_id;
	private Integer member_type; // 1 == primary 2 == secondary 3 == children of
	private String f_name;
	private String l_name;
	private String email;
	private String htmlPage;
	private Boolean exists;
	private String nick_name;

	public AuthDTO() {
	}

	public AuthDTO(Integer p_id, Integer ms_id, Integer memberType, String fname, String lname, String email, String htmlPage, Boolean exists, String nname) {
		this.p_id = p_id;
		this.ms_id = ms_id;
		this.member_type = memberType;
		this.f_name = fname;
		this.l_name = lname;
		this.email = email;
		this.htmlPage = htmlPage;
		this.exists = exists;
		this.nick_name = nname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public Integer getMs_id() {
		return ms_id;
	}

	public void setMs_id(Integer ms_id) {
		this.ms_id = ms_id;
	}

	public Integer getMember_type() {
		return member_type;
	}

	public void setMember_type(Integer member_type) {
		this.member_type = member_type;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
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

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public void copy(AuthDTO authDTO) {
		this.p_id = authDTO.getP_id();
		this.ms_id = authDTO.getMs_id();
		this.member_type = authDTO.getMember_type();
		this.f_name = authDTO.getF_name();
		this.l_name = authDTO.getL_name();
		this.email = authDTO.getEmail();
		this.htmlPage = authDTO.getHtmlPage();
		this.exists = authDTO.getExists();
		this.nick_name = authDTO.getNick_name();
	}

	@Override
	public String toString() {
		return "AuthDTO{" +
				"p_id=" + p_id +
				", ms_id=" + ms_id +
				", member_type=" + member_type +
				", f_name='" + f_name + '\'' +
				", l_name='" + l_name + '\'' +
				", email='" + email + '\'' +
				", htmlPage='" + htmlPage + '\'' +
				", exists=" + exists +
				", nick_name='" + nick_name + '\'' +
				'}';
	}
}
