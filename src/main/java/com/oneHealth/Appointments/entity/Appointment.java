package com.oneHealth.Appointments.entity;

import java.sql.Time;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Entity class representing an appointment.
 * This class is mapped to a database table to store appointment details.
 * @author Anup
 * @version 1.0
 */
@Entity
public class Appointment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long appointment_id;
	private long doctorId;
	private long patientId;
	private String patient_name;
	private int age;
	private String gender;
	private String description;
	private Date date;
	private Time appointmentTime;
	private String status;
	private String type;
	private String payment_mode;
	private String transaction_id;
	private String address;
	private int amount_paid;
	private String doctorName;
	
	// Constructors, getters, and setters are provided below...
	
	
	// Default constructor for Appointment class.
	public Appointment() 
	{
		
	}

	public Appointment(long appointment_id, long doctorId, long patientId, String patient_name, int age, String gender,
			String description, Date date, Time appointmentTime, String status, String type, String payment_mode,
			String transaction_id, String address, int amount_paid, String doctorName) {
		super();
		this.appointment_id = appointment_id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.patient_name = patient_name;
		this.age = age;
		this.gender = gender;
		this.description = description;
		this.date = date;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.type = type;
		this.payment_mode = payment_mode;
		this.transaction_id = transaction_id;
		this.address = address;
		this.amount_paid = amount_paid;
		this.doctorName = doctorName;
	}

	public long getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(long appointment_id) {
		this.appointment_id = appointment_id;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Time appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(int amount_paid) {
		this.amount_paid = amount_paid;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Override
	public String toString() {
		return "Appointment [appointment_id=" + appointment_id + ", doctorId=" + doctorId + ", patientId=" + patientId
				+ ", patient_name=" + patient_name + ", age=" + age + ", gender=" + gender + ", description="
				+ description + ", date=" + date + ", appointmentTime=" + appointmentTime + ", status=" + status
				+ ", type=" + type + ", payment_mode=" + payment_mode + ", transaction_id=" + transaction_id
				+ ", address=" + address + ", amount_paid=" + amount_paid + ", doctorName=" + doctorName + "]";
	}
	
	
	
	
}
