package com.ecsail.Gybe.dto;

public class BoatListDTO {


	protected int boatId;
	protected int msId;
	protected String manufacturer;
	protected String manufactureYear;
	protected String registrationNum;
	protected String model;
	protected String boatName;
	protected String sailNumber;
	protected boolean hasTrailer;
	protected String loa;
	protected String displacement;
	protected String keel;
	protected String phrf;
	protected String draft;
	protected String beam;
	protected String lwl;
	protected boolean aux;
	protected int membershipId;
	protected String lName;
	protected String fName;
	protected int numberOfImages;

	public BoatListDTO(int boatId, int msId, String manufacturer, String manufactureYear, String registrationNum, String model, String boatName, String sailNumber, boolean hasTrailer, String loa, String displacement, String keel, String phrf, String draft, String beam, String lwl, boolean aux, int membershipId, String lName, String fName, int numberOfImages) {
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
		this.membershipId = membershipId;
		this.lName = lName;
		this.fName = fName;
		this.numberOfImages = numberOfImages;
	}

	public int getBoatId() {
		return boatId;
	}

	public void setBoatId(int boatId) {
		this.boatId = boatId;
	}

	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
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

	public boolean isHasTrailer() {
		return hasTrailer;
	}

	public void setHasTrailer(boolean hasTrailer) {
		this.hasTrailer = hasTrailer;
	}

	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
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

	public boolean isAux() {
		return aux;
	}

	public void setAux(boolean aux) {
		this.aux = aux;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public int getNumberOfImages() {
		return numberOfImages;
	}

	public void setNumberOfImages(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

	@Override
	public String toString() {
		return "BoatListDTO{" +
				"boatId=" + boatId +
				", msId=" + msId +
				", manufacturer='" + manufacturer + '\'' +
				", manufactureYear='" + manufactureYear + '\'' +
				", registrationNum='" + registrationNum + '\'' +
				", model='" + model + '\'' +
				", boatName='" + boatName + '\'' +
				", sailNumber='" + sailNumber + '\'' +
				", hasTrailer=" + hasTrailer +
				", loa='" + loa + '\'' +
				", displacement='" + displacement + '\'' +
				", keel='" + keel + '\'' +
				", phrf='" + phrf + '\'' +
				", draft='" + draft + '\'' +
				", beam='" + beam + '\'' +
				", lwl='" + lwl + '\'' +
				", aux=" + aux +
				", membershipId=" + membershipId +
				", lName='" + lName + '\'' +
				", fName='" + fName + '\'' +
				", numberOfImages=" + numberOfImages +
				'}';
	}
}
