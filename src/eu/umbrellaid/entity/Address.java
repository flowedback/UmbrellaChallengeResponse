package eu.umbrellaid.entity;

import java.io.Serializable;

public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660206442556621710L;

	private Honorific title;

	private String firstName;

	private String middleInitial;

	private String lastName;

	private Gender gender;

	private String nationality;

	private String affiliation;

	private String email;

	private String phone;
	
	private String eaahash;

	private String eaakey;
	
	public Honorific getTitle() {
		return title;
	}

	public void setTitle(Honorific title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEaahash() {
		return eaahash;
	}

	public void setEaahash(String eaahash) {
		this.eaahash = eaahash;
	}

	public String getEaakey() {
		return eaakey;
	}

	public void setEaakey(String eaakey) {
		this.eaakey = eaakey;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Title: \t" + title + "\n");
		sb.append("First name: \t" + firstName + "\n");
		sb.append("Middle initial: \t" + middleInitial + "\n");
		sb.append("Last name: \t" + lastName + "\n");
		sb.append("Gender: \t" + gender + "\n");
		sb.append("Nationality: \t" + nationality + "\n");
		sb.append("Affiliation: \t" + affiliation + "\n");
		sb.append("Email: \t" + email + "\n");
		sb.append("Phone: \t" + phone + "\n");
		sb.append("Eaahash: \t" + eaahash + "\n");
		sb.append("Eaakey: \t" + eaakey + "\n");
		return sb.toString();
	}

}
