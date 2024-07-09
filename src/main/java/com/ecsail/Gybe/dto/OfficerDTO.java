package com.ecsail.Gybe.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;

public class OfficerDTO {

	@JsonProperty("officerId")
	private int officerId;

	@JsonProperty("pId")
	private int pId;

	@JsonProperty("boardYear")
	private int boardYear;

	@JsonProperty("officerType")
	private String officerType;

	@JsonProperty("fiscalYear")
	private int fiscalYear;

	public OfficerDTO(int officerId, int pId, int boardYear, String officerType, int fiscalYear) {
		this.officerId = officerId;
		this.pId = pId;
		this.boardYear = boardYear;
		this.officerType = officerType;
		this.fiscalYear = fiscalYear;
	}

	public OfficerDTO() {
	}

	public int getOfficerId() {
		return officerId;
	}

	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getBoardYear() {
		return boardYear;
	}

	public void setBoardYear(int boardYear) {
		this.boardYear = boardYear;
	}

	public String getOfficerType() {
		return officerType;
	}

	public void setOfficerType(String officerType) {
		this.officerType = officerType;
	}

	public int getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	@Override
	public String toString() {
		return "OfficerDTO{" +
				"officerId=" + officerId +
				", pId=" + pId +
				", boardYear='" + boardYear + '\'' +
				", officerType='" + officerType + '\'' +
				", fiscalYear='" + fiscalYear + '\'' +
				'}';
	}
}
