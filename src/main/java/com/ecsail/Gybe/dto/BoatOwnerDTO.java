package com.ecsail.Gybe.dto;


public class BoatOwnerDTO {
	private Integer msid;
	private Integer boat_id;

	public BoatOwnerDTO(Integer msid, Integer boat_id) {
		this.msid = msid;
		this.boat_id = boat_id;
	}

	public Integer getMsid() {
		return msid;
	}

	public void setMsid(Integer msid) {
		this.msid = msid;
	}

	public Integer getBoat_id() {
		return boat_id;
	}

	public void setBoat_id(Integer boat_id) {
		this.boat_id = boat_id;
	}

	@Override
	public String toString() {
		return "BoatOwnerDTO{" +
				"msid=" + msid +
				", boat_id=" + boat_id +
				'}';
	}
}
