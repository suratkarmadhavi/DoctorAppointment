
package com.oneHealth.Appointments.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.AppointmentNotFoundException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;

/**
 * Service interface for handling appointment-related operations.
 * @author Anup
 * @version 1.0
 */
public interface AppointmentService 
{
    /**
     * Save the appointment details into the database.
     * @throws Exception 
     */
    Appointment saveAppointment(Appointment obj) throws Exception;
    
    
    
    /**
     * Find an appointment by patient ID.
     */
    List<Appointment> findByPatientId(long patientId);

    /**
     * Find an appointment by doctor ID.
     */
    List<Appointment> findByDoctorId(long doctorId);

    /**
     * Find appointments by doctor ID and status.
     */
    List<Appointment> findByDoctorIdAndStatus(long doctorId, String status);

    /**
     * Find appointments by patient ID and type.
     */
    List<Appointment> findByPatientIdAndType(long patientId, String type);

    /**
     * Update the status of an appointment by appointment ID.
     */
    void updateAppointmentStatus(long appointmentId, String newStatus) throws AppointmentNotFoundException;

    /**
     * Update the date and time of an appointment by appointment ID.
     */
    Appointment updateAppointmentDateTime(long appointmentId, Date newDate, Time newTime) throws RecordNotFoundException;

    /**
     * Delete an appointment by appointment ID.
     */
    void deleteAppointment(long appointmentId) throws RecordNotFoundException;
    
    
    /**
     * Retrieves a list of appointments scheduled for today.
     *
     * @return List of Appointment objects scheduled for today.
     */
    List<Appointment> getAppointmentsForToday();

    /**
     * Retrieves a list of upcoming appointments with a specific status.
     *
     * @param status The status of the appointments to retrieve.
     * @return List of Appointment objects with the specified status.
     */
    List<Appointment> getUpcomingAppointmentsWithStatus(String status);

    /**
     * Retrieves a list of upcoming appointments for a specific doctor with a given status.
     *
     * @param doctorId The ID of the doctor.
     * @param status   The status of the appointments to retrieve.
     * @return List of Appointment objects for the specified doctor and status.
     * @throws RecordNotFoundException if no upcoming appointments found for the doctor and status.
     */
    List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatus(long doctorId, String status) throws RecordNotFoundException;

    /**
     * Retrieves a list of upcoming appointments for a specific doctor with a given status and type.
     *
     * @param doctorId The ID of the doctor.
     * @param status   The status of the appointments to retrieve.
     * @param type     The type of the appointments to retrieve.
     * @return List of Appointment objects for the specified doctor, status, and type.
     * @throws RecordNotFoundException if no upcoming appointments found for the doctor, status, and type.
     */
//    List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatusAndType(long doctorId, String status, String type) throws RecordNotFoundException;
    List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatusAndType(long doctorId, String type,String Status) throws RecordNotFoundException;

    
    /**
     * Retrieves a list of appointments for today's date with a specific doctor ID and status.
     *
     * @param doctorId The ID of the doctor for whom to retrieve the appointments.
     * @param status The status of the appointments to retrieve (e.g., "Accepted", "Pending", etc.).
     * @return List<Appointment> A list of appointments matching the specified criteria.
     * @throws RecordNotFoundException If no appointments are found for the given criteria.
     */
    List<Appointment> getAppointmentsForTodayByDoctorIdAndStatus(long doctorId, String status) throws RecordNotFoundException;

    /**
     * Retrieves a list of appointments for today's date with a specific patient ID and status.
     *
     * @param patientId The ID of the patient for whom to retrieve the appointments.
     * @param status The status of the appointments to retrieve (e.g., "Accepted", "Pending", etc.).
     * @return List<Appointment> A list of appointments matching the specified criteria.
     * @throws RecordNotFoundException If no appointments are found for the given criteria.
     */
    List<Appointment> getAppointmentsForTodayByPatientIdAndStatus(long patientId, String status) throws RecordNotFoundException;

    /**
     * Retrieves a list of upcoming appointments with a specific patient ID and status.
     *
     * @param patientId The ID of the patient for whom to retrieve the appointments.
     * @param status The status of the appointments to retrieve (e.g., "Accepted", "Pending", etc.).
     * @return List<Appointment> A list of upcoming appointments matching the specified criteria.
     * @throws RecordNotFoundException If no appointments are found for the given criteria.
     */
    List<Appointment> getUpcomingAppointmentsByPatientIdAndStatus(long patientId, String status) throws RecordNotFoundException;

    /**
     * Updates an appointment with the given appointment ID and updated appointment details.
     *
     * @param appointmentId The ID of the appointment to be updated.
     * @param updatedAppointment The updated appointment details.
     * @return Appointment The updated appointment.
     * @throws RecordNotFoundException If the appointment with the given ID is not found.
     */
    Appointment updateAppointment(long appointmentId, Appointment updatedAppointment) throws RecordNotFoundException;
    
    
    /**
     * Retrieves the count of today's appointments for a specific doctor with the given status.
     *
     * @param doctorId The ID of the doctor for whom appointments are counted.
     * @param status   The status of appointments to be counted.
     * @return long The count of today's appointments for the specified doctor and status.
     */
    long getTodayAppointmentsCountByDoctorIdAndStatus(long doctorId, String status);

    /**
     * Retrieves the count of upcoming appointments for a specific doctor with the given status.
     *
     * @param doctorId The ID of the doctor for whom appointments are counted.
     * @param status   The status of appointments to be counted.
     * @return Long The count of upcoming appointments for the specified doctor and status.
     */
    Long getCountOfUpcomingAppointmentsByDoctorIdAndStatus(Long doctorId, String status);

    /**
     * Finds a list of appointments for a specific patient with the given status.
     *
     * @param patientId The ID of the patient for whom appointments are retrieved.
     * @param status    The status of appointments to be retrieved.
     * @return List<Appointment> A list of appointments for the specified patient and status.
     */
    List<Appointment> findByPatientIdAndStatus(long patientId, String status);

    /**
     * Finds a list of not accepted appointments for a specific doctor with the given status.
     *
     * @param doctorId The ID of the doctor for whom appointments are retrieved.
     * @param status   The status of appointments to be retrieved.
     * @return List<Appointment> A list of not accepted appointments for the specified doctor and status.
     */
    List<Appointment> NotAcceptedAppointmentsForRequest(long doctorId, String status);

    /**
     * Finds a list of upcoming appointments for a specific patient.
     *
     * @param patientId The ID of the patient for whom upcoming appointments are retrieved.
     * @return List<Appointment> A list of upcoming appointments for the specified patient.
     */
    List<Appointment> findUpcomingByPatientId(long patientId);

    /**
     * Saves a doctor's appointment.
     *
     * @param appointment The appointment to be saved.
     * @return Appointment The saved appointment.
     * @throws Exception If an error occurs during the saving process.
     */
    Appointment saveDoctorAppointment(Appointment appointment) throws Exception;

    /**
     * Saves a patient's appointment.
     *
     * @param appointment The appointment to be saved.
     * @throws Exception If an error occurs during the saving process.
     */
    void savePatientAppointment(Appointment appointment) throws Exception;

    /**
     * Retrieves a list of appointment times for available slots on a specific date and for a specific doctor.
     *
     * @param doctorId The ID of the doctor for whom available slots are retrieved.
     * @param date     The date for which available slots are retrieved.
     * @return List<Appointment> A list of appointment times for available slots.
     */
    List<Appointment> getAppointmentTimeForSlots(long doctorId, Date date);

    
    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return Appointment The appointment with the specified ID.
     * @throws RecordNotFoundException if no appointment is found with the given ID.
     */
    Appointment getAppointmentById(long appointment_id) throws RecordNotFoundException;



	List<Appointment> getAllAppointments() throws Exception;

	
}

