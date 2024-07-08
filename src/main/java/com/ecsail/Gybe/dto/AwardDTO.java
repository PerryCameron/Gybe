package com.ecsail.Gybe.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;

public class AwardDTO {

	@JsonProperty("awardId")
	private int awardId;

	@JsonProperty("pId")
	private int pId;

	@JsonProperty("awardYear")
	private String awardYear;

	@JsonProperty("awardType")
	private String awardType;

	public AwardDTO(int awardId, int pId, String awardYear, String awardType) {
		this.awardId = awardId;
		this.pId = pId;
		this.awardYear = awardYear;
		this.awardType = awardType;
	}

	public AwardDTO() {
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
