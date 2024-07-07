package com.ecsail.Gybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoatDTO {
	
	private Integer boatId;
	@JsonIgnore // This field is set elsewhere and not mapped directly from the JSON
	private Integer msId; // this is not in database
	private String manufacturer;
	private String manufactureYear;
	private String registrationNum;
	private String model;
	private String boatName;
	private String sailNumber;
	private Boolean hasTrailer;
	private String loa;
	private String displacement;
	private String keel;
	private String phrf;
	private String draft;
	private String beam;
	private String lwl;
	private Boolean aux;

//	public BoatDTO(Integer boatId, Integer msId, String manufacturer, String manufactureYear, String registrationNum, String model, String boatName, String sailNumber, Boolean hasTrailer, String loa, String displacement, String keel, String phrf, String draft, String beam, String lwl, Boolean aux) {
//		this.boatId = boatId;
//		this.msId = msId;
//		this.manufacturer = manufacturer;
//		this.manufactureYear = manufactureYear;
//		this.registrationNum = registrationNum;
//		this.model = model;
//		this.boatName = boatName;
//		this.sailNumber = sailNumber;
//		this.hasTrailer = hasTrailer;
//		this.loa = loa;
//		this.displacement = displacement;
//		this.keel = keel;
//		this.phrf = phrf;
//		this.draft = draft;
//		this.beam = beam;
//		this.lwl = lwl;
//		this.aux = aux;
//	}

	public BoatDTO(@JsonProperty("BOAT_ID") Integer boatId,
				   @JsonProperty("MS_ID") Integer msId,
				   @JsonProperty("MANUFACTURER") String manufacturer,
				   @JsonProperty("MANUFACTURE_YEAR") String manufactureYear,
				   @JsonProperty("REGISTRATION_NUM") String registrationNum,
				   @JsonProperty("MODEL") String model,
				   @JsonProperty("BOAT_NAME") String boatName,
				   @JsonProperty("SAIL_NUMBER") String sailNumber,
				   @JsonProperty("HAS_TRAILER") Boolean hasTrailer,
				   @JsonProperty("LENGTH") String loa,
				   @JsonProperty("WEIGHT") String displacement,
				   @JsonProperty("KEEL") String keel,
				   @JsonProperty("PHRF") String phrf,
				   @JsonProperty("DRAFT") String draft,
				   @JsonProperty("BEAM") String beam,
				   @JsonProperty("LWL") String lwl,
				   @JsonProperty("AUX") Boolean aux) {
		this.boatId = boatId;
		this.msId = msId;
		this.manufacturer = manufacturer;
		this.manufactureYear = manufactureYear;
		this.registrationNum = registrationNum;
		this.model = model;
		this.boatName = boatName;
		this.sailNumber = sailNumber;
		this.hasTrailer = hasTrailer;
		this.loa = loa;
		this.displacement = displacement;
		this.keel = keel;
		this.phrf = phrf;
		this.draft = draft;
		this.beam = beam;
		this.lwl = lwl;
		this.aux = aux;
	}

	public BoatDTO() {
	}

	@JsonProperty("BOAT_ID")
	public Integer getBoatId() {
		return boatId;
	}

	public void setBoatId(Integer boatId) {
		this.boatId = boatId;
	}

	public Integer getMsId() {
		return msId;
	}

	public void setMsId(Integer msId) {
		this.msId = msId;
	}

	@JsonProperty("MANUFACTURER")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@JsonProperty("MANUFACTURE_YEAR")
	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	@JsonProperty("REGISTRATION_NUM")
	public String getRegistrationNum() {
		return registrationNum;
	}

	public void setRegistrationNum(String registrationNum) {
		this.registrationNum = registrationNum;
	}

	@JsonProperty("MODEL")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@JsonProperty("BOAT_NAME")
	public String getBoatName() {
		return boatName;
	}

	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

	@JsonProperty("SAIL_NUMBER")
	public String getSailNumber() {
		return sailNumber;
	}

	public void setSailNumber(String sailNumber) {
		this.sailNumber = sailNumber;
	}

	@JsonProperty("HAS_TRAILER")
	public Boolean getHasTrailer() {
		return hasTrailer;
	}

	public void setHasTrailer(Boolean hasTrailer) {
		this.hasTrailer = hasTrailer;
	}

	@JsonProperty("LENGTH")
	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	@JsonProperty("WEIGHT")
	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	@JsonProperty("KEEL")
	public String getKeel() {
		return keel;
	}

	public void setKeel(String keel) {
		this.keel = keel;
	}

	@JsonProperty("PHRF")
	public String getPhrf() {
		return phrf;
	}

	public void setPhrf(String phrf) {
		this.phrf = phrf;
	}

	@JsonProperty("DRAFT")
	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	@JsonProperty("BEAM")
	public String getBeam() {
		return beam;
	}

	public void setBeam(String beam) {
		this.beam = beam;
	}

	@JsonProperty("LWL")
	public String getLwl() {
		return lwl;
	}

	public void setLwl(String lwl) {
		this.lwl = lwl;
	}

	@JsonProperty("AUX")
	public Boolean getAux() {
		return aux;
	}

	public void setAux(Boolean aux) {
		this.aux = aux;
	}

	@Override
	public String toString() {
		return "BoatDTO{" +
				"boat_id=" + boatId +
				", ms_id=" + msId +
				", manufacturer='" + manufacturer + '\'' +
				", manufacture_year='" + manufactureYear + '\'' +
				", registration_num='" + registrationNum + '\'' +
				", model='" + model + '\'' +
				", boat_name='" + boatName + '\'' +
				", sail_number='" + sailNumber + '\'' +
				", hasTrailer=" + hasTrailer +
				", length='" + loa + '\'' +
				", weight='" + displacement + '\'' +
				", keel='" + keel + '\'' +
				", phrf='" + phrf + '\'' +
				", draft='" + draft + '\'' +
				", beam='" + beam + '\'' +
				", lwl='" + lwl + '\'' +
				", aux=" + aux +
				'}';
	}
}
