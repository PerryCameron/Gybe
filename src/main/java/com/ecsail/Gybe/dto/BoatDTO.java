package com.ecsail.Gybe.dto;

public class BoatDTO {
	
	private Integer boat_id;
	private Integer ms_id;
	private String manufacturer;
	private String manufacture_year;
	private String registration_num;
	private String model;
	private String boat_name;
	private String sail_number;
	private Boolean hasTrailer;
	private String length;
	private String weight;
	private String keel;
	private String phrf;
	private String draft;
	private String beam;
	private String lwl;
	private Boolean aux;

	public BoatDTO(Integer boat_id, Integer ms_id, String manufacturer, String manufacture_year, String registration_num, String model, String boat_name, String sail_number, Boolean hasTrailer, String length, String weight, String keel, String phrf, String draft, String beam, String lwl, Boolean aux) {
		this.boat_id = boat_id;
		this.ms_id = ms_id;
		this.manufacturer = manufacturer;
		this.manufacture_year = manufacture_year;
		this.registration_num = registration_num;
		this.model = model;
		this.boat_name = boat_name;
		this.sail_number = sail_number;
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

	public Integer getBoat_id() {
		return boat_id;
	}

	public void setBoat_id(Integer boat_id) {
		this.boat_id = boat_id;
	}

	public Integer getMs_id() {
		return ms_id;
	}

	public void setMs_id(Integer ms_id) {
		this.ms_id = ms_id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacture_year() {
		return manufacture_year;
	}

	public void setManufacture_year(String manufacture_year) {
		this.manufacture_year = manufacture_year;
	}

	public String getRegistration_num() {
		return registration_num;
	}

	public void setRegistration_num(String registration_num) {
		this.registration_num = registration_num;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBoat_name() {
		return boat_name;
	}

	public void setBoat_name(String boat_name) {
		this.boat_name = boat_name;
	}

	public String getSail_number() {
		return sail_number;
	}

	public void setSail_number(String sail_number) {
		this.sail_number = sail_number;
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
				"boat_id=" + boat_id +
				", ms_id=" + ms_id +
				", manufacturer='" + manufacturer + '\'' +
				", manufacture_year='" + manufacture_year + '\'' +
				", registration_num='" + registration_num + '\'' +
				", model='" + model + '\'' +
				", boat_name='" + boat_name + '\'' +
				", sail_number='" + sail_number + '\'' +
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
