package com.ecsail.Gybe.dto;


import java.util.ArrayList;

public class MembershipListDTO {
	private int msId; /// unique auto key for Membership
	private int pId;  /// pid of Main Member
	private int membershipId;
	private String joinDate; // TODO change this to objectProperty<LocalDate>
	private String memType;  // Type of Membership (Family, Regular, Lake Associate(race fellow), Social
	private String address;
	private String city;
	private String state;
	private String zip;
	private String lastName;
	private String firstName;
	private String slip;
	private int subLeaser;
	private int selectedYear;
	private ArrayList<BoatDTO> boatDTOS = new ArrayList<>();
	private ArrayList<NotesDTO> notesDTOS = new ArrayList<>();
	private ArrayList<MembershipIdDTO> membershipIdDTOS = new ArrayList<>();
	private ArrayList<InvoiceDTO> invoiceDTOS = new ArrayList<>();
	private ArrayList<PersonDTO> personDTOS = new ArrayList<>();


	public MembershipListDTO(int msId, int pId, int membershipId, String joinDate, String memType, String address, String city, String state, String zip, String lastName, String firstName, String slip, int subLeaser, int selectedYear) {
		this.msId = msId;
		this.pId = pId;
		this.membershipId = membershipId;
		this.joinDate = joinDate;
		this.memType = memType;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.lastName = lastName;
		this.firstName = firstName;
		this.slip = slip;
		this.subLeaser = subLeaser;
		this.selectedYear = selectedYear;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSlip() {
		return slip;
	}

	public void setSlip(String slip) {
		this.slip = slip;
	}

	public int getSubLeaser() {
		return subLeaser;
	}

	public void setSubLeaser(int subLeaser) {
		this.subLeaser = subLeaser;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}

	public ArrayList<BoatDTO> getBoatDTOS() {
		return boatDTOS;
	}

	public void setBoatDTOS(ArrayList<BoatDTO> boatDTOS) {
		this.boatDTOS = boatDTOS;
	}

	public ArrayList<NotesDTO> getNotesDTOS() {
		return notesDTOS;
	}

	public void setNotesDTOS(ArrayList<NotesDTO> notesDTOS) {
		this.notesDTOS = notesDTOS;
	}

	public ArrayList<MembershipIdDTO> getMembershipIdDTOS() {
		return membershipIdDTOS;
	}

	public void setMembershipIdDTOS(ArrayList<MembershipIdDTO> membershipIdDTOS) {
		this.membershipIdDTOS = membershipIdDTOS;
	}

	public ArrayList<InvoiceDTO> getInvoiceDTOS() {
		return invoiceDTOS;
	}

	public void setInvoiceDTOS(ArrayList<InvoiceDTO> invoiceDTOS) {
		this.invoiceDTOS = invoiceDTOS;
	}

	public ArrayList<PersonDTO> getPersonDTOS() {
		return personDTOS;
	}

	public void setPersonDTOS(ArrayList<PersonDTO> personDTOS) {
		this.personDTOS = personDTOS;
	}

	@Override
	public String toString() {
		return "MembershipListDTO{" +
				"msId=" + msId +
				", pId=" + pId +
				", membershipId=" + membershipId +
				", joinDate='" + joinDate + '\'' +
				", memType='" + memType + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", slip='" + slip + '\'' +
				", subLeaser=" + subLeaser +
				", selectedYear=" + selectedYear +
				", boatDTOS=" + boatDTOS +
				", notesDTOS=" + notesDTOS +
				", membershipIdDTOS=" + membershipIdDTOS +
				", invoiceDTOS=" + invoiceDTOS +
				", personDTOS=" + personDTOS +
				'}';
	}
}
