package com.ecsail.Gybe.dto;



public class MembershipIdDTO {
	private Integer mId;
	private String fiscalYear;
	private Integer msId;
	private String membershipId;
	private boolean renew;
	private String memType;
	private boolean selected;
	private boolean lateRenew;
	
	public MembershipIdDTO(Integer mId, String fiscalYear, Integer msId, String membershipId,
                           Boolean renew, String memType, Boolean selected, Boolean lateRenew) {
		this.mId = mId;
		this.fiscalYear = new String(fiscalYear);
		this.msId = msId;
		this.membershipId = membershipId;
		this.renew = renew;
		this.memType = memType;
		this.selected = selected;
		this.lateRenew = lateRenew;
	}


	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Integer getMsId() {
		return msId;
	}

	public void setMsId(Integer msId) {
		this.msId = msId;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public boolean isRenew() {
		return renew;
	}

	public void setRenew(boolean renew) {
		this.renew = renew;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isLateRenew() {
		return lateRenew;
	}

	public void setLateRenew(boolean lateRenew) {
		this.lateRenew = lateRenew;
	}

	@Override
	public String toString() {
		return "MembershipIdDTO{" +
				"mId=" + mId +
				", fiscalYear='" + fiscalYear + '\'' +
				", msId=" + msId +
				", membershipId='" + membershipId + '\'' +
				", renew=" + renew +
				", memType='" + memType + '\'' +
				", selected=" + selected +
				", lateRenew=" + lateRenew +
				'}';
	}
}
