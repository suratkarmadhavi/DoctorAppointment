package com.oneHealth.Appointments.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneHealth.Appointments.entity.Appointment;

/**
 * Repository interface for handling database operations related to the Appointment entity.
 * This interface extends the JpaRepository to provide basic CRUD operations on the Appointment table.
 * @author Anup
 * @version 3.9.10
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    /**
     * Find an appointment by patient ID.
     * @param patientId The ID of the patient.
     * @return An optional containing the appointment if found, otherwise empty.
     */
    List<Appointment> findByPatientId(Long patientId);
    
    /**
     * Find an appointment by doctor ID.
     * @param doctorId The ID of the doctor.
     * @return An optional containing the appointment if found, otherwise empty.
     */
    List<Appointment> findByDoctorId(Long doctorId);
    
    /**
     * Find appointments by type.
     * @param type The type of the appointment.
     * @return An optional containing the appointment if found, otherwise empty.
     */
    Optional<Appointment> findByType(String type);
    
    /**
     * Find appointments by doctor ID and status.
     * @param doctorId The ID of the doctor.
     * @param status The status of the appointment.
     * @return A list of appointments with the specified doctor ID and status.
     */
    List<Appointment> findByDoctorIdAndStatus(long doctorId, String status);
    
    /**
     * Find appointments by doctor ID and type.
     * @param doctorId The ID of the doctor.
     * @param type The type of the appointment.
     * @return A list of appointments with the specified doctor ID and type.
     */
    List<Appointment> findByDoctorIdAndType(long doctorId, String type);
    
    /**
     * Find appointments by patient ID and type.
     * @param patientId The ID of the patient.
     * @param type The type of the appointment.
     * @return A list of appointments with the specified patient ID and type.
     */
    List<Appointment> findByPatientIdAndType(long patientId, String type);
}

