package com.oneHealth.Appointments.DTO;
//Import necessary Java class for Date handling.
import java.sql.Date;
/**
 * Entity class representing an patient.
 * This class is mapped to a database table to store patient details.
 * 
 * @author Anup
 * @version 1.0
 */
public class Patient {

 // Declare instance variables to store patient details.
 private long patientId;
 private long userId;
 private String firstName;
 private String lastName;
 private String mobileNumber;
 private String address;
 private int pinCode;
 private String country;
 private String city;
 private String gender;
 private int age;
 private Date dob;
 private String bloodGroup;
 private float height;
 private float weight;
 private String maritalStatus;
 private String emailId;

 // Default constructor for the Patient class.
 public Patient() {
     super();
 }

 // Parameterized constructor for the Patient class to initialize all fields.
 public Patient(long patientId, long userId, String firstName, String lastName, String mobileNumber,
         String address, int pinCode, String country, String city, String gender, int age, Date dob,
         String bloodGroup, float height, float weight, String maritalStatus, String emailId) {
     super();
     this.patientId = patientId;
     this.userId = userId;
     this.firstName = firstName;
     this.lastName = lastName;
     this.mobileNumber = mobileNumber;
     this.address = address;
     this.pinCode = pinCode;
     this.country = country;
     this.city = city;
     this.gender = gender;
     this.age = age;
     this.dob = dob;
     this.bloodGroup = bloodGroup;
     this.height = height;
     this.weight = weight;
     this.maritalStatus = maritalStatus;
     this.emailId = emailId;
 }

 // Getter and setter methods for each attribute.
 public long getPatientId() {
     return patientId;
 }

 public void setPatientId(long patientId) {
     this.patientId = patientId;
 }

 public long getUserId() {
     return userId;
 }

 public void setUserId(long userId) {
     this.userId = userId;
 }

 public String getFirstName() {
     return firstName;
 }

 public void setFirstName(String firstName) {
     this.firstName = firstName;
 }

 public String getLastName() {
     return lastName;
 }

 public void setLastName(String lastName) {
     this.lastName = lastName;
 }

 public String getMobileNumber() {
     return mobileNumber;
 }

 public void setMobileNumber(String mobileNumber) {
     this.mobileNumber = mobileNumber;
 }

 public String getAddress() {
     return address;
 }

 public void setAddress(String address) {
     this.address = address;
 }

 public int getPinCode() {
     return pinCode;
 }

 public void setPinCode(int pinCode) {
     this.pinCode = pinCode;
 }

 public String getCountry() {
     return country;
 }

 public void setCountry(String country) {
     this.country = country;
 }

 public String getCity() {
     return city;
 }

 public void setCity(String city) {
     this.city = city;
 }

 public String getGender() {
     return gender;
 }

 public void setGender(String gender) {
     this.gender = gender;
 }

 public int getAge() {
     return age;
 }

 public void setAge(int age) {
     this.age = age;
 }

 public Date getDob() {
     return dob;
 }

 public void setDob(Date dob) {
     this.dob = dob;
 }

 public String getBloodGroup() {
     return bloodGroup;
 }

 public void setBloodGroup(String bloodGroup) {
     this.bloodGroup = bloodGroup;
 }

 public float getHeight() {
     return height;
 }

 public void setHeight(float height) {
     this.height = height;
 }

 public float getWeight() {
     return weight;
 }

 public void setWeight(float weight) {
     this.weight = weight;
 }

 public String getMaritalStatus() {
     return maritalStatus;
 }

 public void setMaritalStatus(String maritalStatus) {
     this.maritalStatus = maritalStatus;
 }

 public String getEmailId() {
     return emailId;
 }

 public void setEmailId(String emailId) {
     this.emailId = emailId;
 }

 // Override the toString method to provide a human-readable representation of the object.
 @Override
 public String toString() {
     return "Patient [patientId=" + patientId + ", userId=" + userId + ", firstName=" + firstName + ", lastName="
             + lastName + ", mobileNumber=" + mobileNumber + ", address=" + address + ", pinCode=" + pinCode
             + ", country=" + country + ", city=" + city + ", gender=" + gender + ", age=" + age + ", dob=" + dob
             + ", bloodGroup=" + bloodGroup + ", height=" + height + ", weight=" + weight + ", maritalStatus="
             + maritalStatus + ", emailId=" + emailId + "]";
 }
}
