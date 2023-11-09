package com.ecsail.Gybe.dto;



import java.time.Year;

public class AwardDTO {
	
	private int awardId;
	private int pId;
	private String awardYear;
	private String awardType;

	public AwardDTO(int awardId, int pId, String awardYear, String awardType) {
		this.awardId = awardId;
		this.pId = pId;
		this.awardYear = awardYear;
		this.awardType = awardType;
	}

	public int getAwardId() {
		return awardId;
	}

	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getAwardYear() {
		return awardYear;
	}

	public void setAwardYear(String awardYear) {
		this.awardYear = awardYear;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	@Override
	public String toString() {
		return "AwardDTO{" +
				"awardId=" + awardId +
				", pId=" + pId +
				", awardYear='" + awardYear + '\'' +
				", awardType='" + awardType + '\'' +
				'}';
	}
}
