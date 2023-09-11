package com.oneHealth.Appointments.repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneHealth.Appointments.entity.Appointment;

/**
 * Repository interface for handling database operations related to the
 * Appointment entity. This interface extends the JpaRepository to provide basic
 * CRUD operations on the Appointment table.
 * 
 * @author Anup
 * @version 1.0
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	/**
	 * Find an appointment by patient ID.
	 * 
	 * @param patientId The ID of the patient.
	 * @return An optional containing the appointment if found, otherwise empty.
	 */
	List<Appointment> findByPatientId(Long patientId);

	/**
	 * Find an appointment by doctor ID.
	 * 
	 * @param doctorId The ID of the doctor.
	 * @return An optional containing the appointment if found, otherwise empty.
	 */
	List<Appointment> findByDoctorId(Long doctorId);

	/**
	 * Find appointments by type.
	 * 
	 * @param type The type of the appointment.
	 * @return An optional containing the appointment if found, otherwise empty.
	 */
	Optional<Appointment> findByType(String type);

	/**
	 * Find appointments by doctor ID and status.
	 * 
	 * @param doctorId The ID of the doctor.
	 * @param status   The status of the appointment.
	 * @return A list of appointments with the specified doctor ID and status.
	 */
	List<Appointment> findByDoctorIdAndStatus(long doctorId, String status);

	/**
	 * Find appointments by doctor ID and type.
	 * 
	 * @param doctorId The ID of the doctor.
	 * @param type     The type of the appointment.
	 * @return A list of appointments with the specified doctor ID and type.
	 */
	List<Appointment> findByDoctorIdAndType(long doctorId, String type);

	/**
	 * Find appointments by patient ID and type.
	 * 
	 * @param patientId The ID of the patient.
	 * @param type      The type of the appointment.
	 * @return A list of appointments with the specified patient ID and type.
	 */
	List<Appointment> findByPatientIdAndType(long patientId, String type);

	// Retrieve a list of appointments based on the provided currentDate.
	// The method returns appointments scheduled on the given currentDate.
	List<Appointment> findByDate(LocalDate currentDate);

	// Retrieve a list of upcoming appointments after the specified date with the
	// given status.
	// The method returns appointments that are scheduled after the provided date
	// and have the specified status.
	List<Appointment> findByDateAfterAndStatus(Date date, String status);

	// Retrieve a list of upcoming appointments after the specified date for the
	// given doctorId and status.
	// The method returns appointments that are scheduled after the provided date,
	// for the specified doctorId, and have the given status.
	List<Appointment> findByDateAfterAndDoctorIdAndStatus(LocalDate date, long doctorId, String status);

	// Retrieve a list of upcoming appointments after the specified date for the
	// given doctorId, status, and type.
	// The method returns appointments that are scheduled after the provided date,
	// for the specified doctorId, have the given status, and belong to the
	// specified type.
	List<Appointment> findByDateAfterAndDoctorIdAndStatusAndType(Date date, long doctorId, String status, String type);

	List<Appointment> findByDateAndDoctorIdAndStatusAndType(Date date, long doctorId, String status, String type);

	/**
	 * Retrieves a list of appointments for a specific date, doctor ID, and status.
	 *
	 * @param currentDate The specific date for which to retrieve appointments.
	 * @param doctorId    The ID of the doctor for whom to retrieve the
	 *                    appointments.
	 * @param status      The status of the appointments to retrieve (e.g.,
	 *                    "Accepted", "Pending", etc.).
	 * @return List<Appointment> A list of appointments matching the specified
	 *         criteria.
	 */
	List<Appointment> findByDateAndDoctorIdAndStatus(LocalDate currentDate, long doctorId, String status);

	/**
	 * Retrieves a list of appointments for a specific date, patient ID, and status.
	 *
	 * @param date      The specific date for which to retrieve appointments.
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @param status    The status of the appointments to retrieve (e.g.,
	 *                  "Accepted", "Pending", etc.).
	 * @return List<Appointment> A list of appointments matching the specified
	 *         criteria.
	 */
	List<Appointment> findByDateAndPatientIdAndStatus(LocalDate date, long patientId, String status);

	/**
	 * Retrieves a list of upcoming appointments for a specific date, patient ID,
	 * and status.
	 *
	 * @param date      The specific date for which to retrieve upcoming
	 *                  appointments.
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @param status    The status of the appointments to retrieve (e.g.,
	 *                  "Accepted", "Pending", etc.).
	 * @return List<Appointment> A list of upcoming appointments matching the
	 *         specified criteria.
	 */
	List<Appointment> findByDateAfterAndPatientIdAndStatus(Date date, long patientId, String status);

	/**
	 * Retrieves a count of upcoming appointments for a specific date, Doctor ID,
	 * and status.
	 *
	 * @param date     The specific date for which to retrieve upcoming
	 *                 appointments.
	 * @param doctorId The ID of the patient for whom to retrieve the appointments.
	 * @param status   The status of the appointments to retrieve (e.g., "Accepted",
	 *                 "Pending", etc.).
	 * @return A count of upcoming appointments matching the specified criteria.
	 */
	long countByDoctorIdAndStatusAndDate(long doctorId, String status, LocalDate date);

	/**
	 * Retrieves a list of appointments for a patient ID, and status.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @param status    The status of the appointments to retrieve (e.g.,
	 *                  "Accepted", "Pending", etc.).
	 * @return List<Appointment> A list of appointments matching the specified
	 *         criteria.
	 */
	List<Appointment> findByPatientIdAndStatus(long patientId, String status);

	/**
	 * Retrieves a list of upcoming appointments for a specific date, patient ID.
	 *
	 * @param date      The specific date for which to retrieve upcoming
	 *                  appointments.
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @return List<Appointment> A list of upcoming appointments matching the
	 *         specified criteria.
	 */
	List<Appointment> findByDateAndPatientId(LocalDate date, long patientId);

	/**
	 * Retrieves a list of upcoming appointments for a specific date, patient ID.
	 *
	 * @param date      The specific date for which to retrieve upcoming
	 *                  appointments.
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @return List<Appointment> A list of upcoming appointments matching the
	 *         specified criteria.
	 */
	List<Appointment> findByDateAfterAndPatientId(LocalDate currentDate, long patientId);

	/**
	 * Checks If Appointment Already exists or not
	 */
	boolean existsByDoctorIdAndAppointmentTimeAndDate(long doctorId, Time appointmentTime, Date date);

	/**
	 * Retrieves a list of appointments for a specific date, doctor ID.
	 *
	 * @param date      The specific date for which to retrieve upcoming
	 *                  appointments.
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @return List<Appointment> A list of appointments matching the specified
	 *         criteria.
	 */
	List<Appointment> findByDoctorIdAndDate(long doctorId, Date date);

	List<Appointment> findAllAppointmentsByDoctorIdAndTypeAndStatusAndDate(long doctorId, String type, String status, Date todayDate);

}
