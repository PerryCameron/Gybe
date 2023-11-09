package com.ecsail.Gybe.dto;


import java.time.Year;

public class OfficerDTO {

	private int officerId;
	private int pId;
	private String boardYear;
	private String officerType;
	private String fiscalYear;

	public OfficerDTO(int officerId, int pId, String boardYear, String officerType, String fiscalYear) {
		this.officerId = officerId;
		this.pId = pId;
		this.boardYear = boardYear;
		this.officerType = officerType;
		this.fiscalYear = fiscalYear;
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

	public String getBoardYear() {
		return boardYear;
	}

	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}

	public String getOfficerType() {
		return officerType;
	}

	public void setOfficerType(String officerType) {
		this.officerType = officerType;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
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
