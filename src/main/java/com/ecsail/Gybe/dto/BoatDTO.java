package com.ecsail.Gybe.dto;

public class BoatDTO {
	
	private Integer boatId;
	private Integer msId;
	private String manufacturer;
	private String manufactureYear;
	private String registrationNum;
	private String model;
	private String boatName;
	private String sailNumber;
	private Boolean hasTrailer;
	private String length;
	private String weight;
	private String keel;
	private String phrf;
	private String draft;
	private String beam;
	private String lwl;
	private Boolean aux;

	public BoatDTO(Integer boatId, Integer msId, String manufacturer, String manufactureYear, String registrationNum, String model, String boatName, String sailNumber, Boolean hasTrailer, String length, String weight, String keel, String phrf, String draft, String beam, String lwl, Boolean aux) {
		this.boatId = boatId;
		this.msId = msId;
		this.manufacturer = manufacturer;
		this.manufactureYear = manufactureYear;
		this.registrationNum = registrationNum;
		this.model = model;
		this.boatName = boatName;
		this.sailNumber = sailNumber;
		this.hasTrailer = hasTrailer;
		this.length = length;
		this.weight = weight;
		this.keel = keel;
		this.phrf = phrf;
		this.draft = draft;
		this.beam = beam;
		this.lwl = lwl;
		this.aux = aux;
	}

	public BoatDTO() {
	}

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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public String getRegistrationNum() {
		return registrationNum;
	}

	public void setRegistrationNum(String registrationNum) {
		this.registrationNum = registrationNum;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBoatName() {
		return boatName;
	}

	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

	public String getSailNumber() {
		return sailNumber;
	}

	public void setSailNumber(String sailNumber) {
		this.sailNumber = sailNumber;
	}

	public Boolean getHasTrailer() {
		return hasTrailer;
	}

	public void setHasTrailer(Boolean hasTrailer) {
		this.hasTrailer = hasTrailer;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getKeel() {
		return keel;
	}

	public void setKeel(String keel) {
		this.keel = keel;
	}

	public String getPhrf() {
		return phrf;
	}

	public void setPhrf(String phrf) {
		this.phrf = phrf;
	}

	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	public String getBeam() {
		return beam;
	}

	public void setBeam(String beam) {
		this.beam = beam;
	}

	public String getLwl() {
		return lwl;
	}

	public void setLwl(String lwl) {
		this.lwl = lwl;
	}

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
				", length='" + length + '\'' +
				", weight='" + weight + '\'' +
				", keel='" + keel + '\'' +
				", phrf='" + phrf + '\'' +
				", draft='" + draft + '\'' +
				", beam='" + beam + '\'' +
				", lwl='" + lwl + '\'' +
				", aux=" + aux +
				'}';
	}
}
