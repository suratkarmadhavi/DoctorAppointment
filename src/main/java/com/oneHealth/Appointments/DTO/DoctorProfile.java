package com.oneHealth.Appointments.DTO;
//Import necessary Java class for Date handling.
import java.sql.Date;

/**
* This class represents the DoctorProfile entity used to store information about a doctor's profile.
* Doctors' personal details, qualifications, experience, and document links are stored as attributes.
* 
* @author Anup
* @version 1.0
*/
public class DoctorProfile 
{
	// Primary key for the entity. The @Id annotation marks this field as the primary key.
	private long doctor_id;
	
	private String first_name;
	private String last_name;
	private String email;
	private String contact;
	private String city;
	private String specialization;
	private String license_number;
	private Date birth_date;
	private String gender;
	private String blood_group;
	private String degree;
	private int passout_year;
	private String university;
	private String biography;
	private float experiance;
	private String photoId;
	private String panId;
	private String aadharId;
	
	// Default constructor required by JPA. Should be available for every entity.
	public DoctorProfile() 
	{
		// Default constructor
	}

	// Parameterized constructor for DoctorProfile class to initialize all fields.
	public DoctorProfile(long doctor_id, String first_name, String last_name, String email, String contact, String city ,
			String specialization, String license_number, Date birth_date, String gender, String blood_group,
			String degree, int passout_year, String university, String biography, float experiance, String photoId,
			String panId, String aadharId) {
		super();
		this.doctor_id = doctor_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.contact = contact;
		this.city = city;
		this.specialization = specialization;
		this.license_number = license_number;
		this.birth_date = birth_date;
		this.gender = gender;
		this.blood_group = blood_group;
		this.degree = degree;
		this.passout_year = passout_year;
		this.university = university;
		this.biography = biography;
		this.experiance = experiance;
		this.photoId = photoId;
		this.panId = panId;
		this.aadharId = aadharId;
	}

	// Getter and setter methods for each attribute.
	public long getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(long doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getLicense_number() {
		return license_number;
	}

	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public int getPassout_year() {
		return passout_year;
	}

	public void setPassout_year(int passout_year) {
		this.passout_year = passout_year;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public float getExperiance() {
		return experiance;
	}

	public void setExperiance(float experiance) {
		this.experiance = experiance;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPanId() {
		return panId;
	}

	public void setPanId(String panId) {
		this.panId = panId;
	}

	public String getAadharId() {
		return aadharId;
	}

	public void setAadharId(String aadharId) {
		this.aadharId = aadharId;
	}

	// Override the toString method to provide a human-readable representation of the object.
	@Override
	public String toString() {
		return "DoctorProfile [doctor_id=" + doctor_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", email=" + email + ", contact=" + contact + ", city=" + city + ", specialization=" + specialization
				+ ", license_number=" + license_number + ", birth_date=" + birth_date + ", gender=" + gender
				+ ", blood_group=" + blood_group + ", degree=" + degree + ", passout_year=" + passout_year
				+ ", university=" + university + ", biography=" + biography + ", experiance=" + experiance
				+ ", photoId=" + photoId + ", panId=" + panId + ", aadharId=" + aadharId + "]";
	}
}
