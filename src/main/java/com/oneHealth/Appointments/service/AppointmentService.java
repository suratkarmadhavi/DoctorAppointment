
package com.oneHealth.Appointments.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.ProfileNotFoundException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;

/**
 * Service interface for handling appointment-related operations.
 * @author Anup
 * @version 3.9.10
 */
public interface AppointmentService 
{
    /**
     * Save the appointment details into the database.
     */
    Appointment saveAppointment(Appointment obj);

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
    void updateAppointmentStatus(long appointmentId, String newStatus) throws ProfileNotFoundException;

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
    List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatusAndType(long doctorId, String status, String type) throws RecordNotFoundException;

    
    List<Appointment> getAppointmentsForTodayByDoctorIdAndStatus(long doctorId, String status) throws RecordNotFoundException;

}

