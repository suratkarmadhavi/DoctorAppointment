
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
}

