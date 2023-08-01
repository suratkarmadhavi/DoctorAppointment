package com.oneHealth.Appointments.serviceImplementation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.ProfileNotFoundException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;
import com.oneHealth.Appointments.repository.AppointmentRepository;
import com.oneHealth.Appointments.service.AppointmentService;

/**
 * Service implementation class that provides the business logic for handling appointment-related operations.
 * @author Anup
 * @version 3.9.10
 */
@Service
public class AppointmentServiceImplementation implements AppointmentService
{
    @Autowired
    private AppointmentRepository repo;
    
    
    // Save the appointment details into the database.
    @Override
    public Appointment saveAppointment(Appointment obj) {
        // Save the appointment details into the database.
        return repo.save(obj);
    }

    
    // Find an appointment by patient ID.
    @Override
    public List<Appointment> findByPatientId(long patientId) {
        // Find an appointment by patient ID.
        return repo.findByPatientId(patientId);
    }
    
    
    // Find an appointment by doctor ID.
    @Override
    public List<Appointment> findByDoctorId(long doctorId) {
        return repo.findByDoctorId(doctorId);
    }

    
    // Find appointments by doctor ID and status.
    @Override
    public List<Appointment> findByDoctorIdAndStatus(long doctorId, String status) {
        return repo.findByDoctorIdAndStatus(doctorId, status);
    }

    
    // Find appointments by patient ID and type.
    @Override
    public List<Appointment> findByPatientIdAndType(long patientId, String type) {
        return repo.findByPatientIdAndType(patientId, type);
    }

    
    // Update the status of an appointment by appointment ID.
    @Override
    public void updateAppointmentStatus(long appointment_id, String newStatus) throws ProfileNotFoundException {
        
        Appointment appointment = repo.findById(appointment_id)
                .orElseThrow(() -> new ProfileNotFoundException("Appointment not found with ID: " + appointment_id));

        appointment.setStatus(newStatus);
        repo.save(appointment);
    }

    
    // Update the date and time of an appointment by appointment ID.
    @Override
    public Appointment updateAppointmentDateTime(long appointment_id, Date newDate, Time newTime) throws RecordNotFoundException {
        
        Appointment appointment = repo.findById(appointment_id)
                .orElseThrow(() -> new RecordNotFoundException("No Appointment Found with ID: " + appointment_id));

        appointment.setDate(newDate);
        appointment.setAppointment_time(newTime);

        return repo.save(appointment);
    }

    
    // Delete an appointment by appointment ID.
    @Override
    public void deleteAppointment(long appointment_id) throws RecordNotFoundException {
        
        Appointment appointment = repo.findById(appointment_id)
                .orElseThrow(() -> new RecordNotFoundException("No Appointment Found with ID: " + appointment_id));
        
        repo.delete(appointment);
    }


    @Override
    public List<Appointment> getAppointmentsForToday() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Retrieve appointments for today's date
        return repo.findByDate(currentDate);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsWithStatus(String status) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(currentDate);

        // Use the sqlDate and status to fetch upcoming appointments with the specified status
        return repo.findByDateAfterAndStatus(sqlDate, status);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatus(long doctorId, String status) throws RecordNotFoundException {
        // Get the current date
        Date todayDate = Date.valueOf(LocalDate.now());

        // Call the custom repository method to fetch upcoming appointments for the specified doctorId and status
        List<Appointment> upcomingAppointments = repo.findByDateAfterAndDoctorIdAndStatus(todayDate, doctorId, status);

        if (upcomingAppointments.isEmpty()) {
            throw new RecordNotFoundException("No upcoming appointments found for Doctor ID: " + doctorId + " and status: " + status);
        }

        return upcomingAppointments;
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatusAndType(long doctorId, String status, String type) throws RecordNotFoundException {
        // Get the current date
        Date todayDate = Date.valueOf(LocalDate.now());

        // Call the custom repository method to fetch upcoming appointments for the specified doctorId, status, and type
        List<Appointment> upcomingAppointments = repo.findByDateAfterAndDoctorIdAndStatusAndType(todayDate, doctorId, status, type);

        if (upcomingAppointments.isEmpty()) {
            throw new RecordNotFoundException("No upcoming appointments found for Doctor ID: " + doctorId +
                    ", status: " + status + ", and type: " + type);
        }

        return upcomingAppointments;
    }


	@Override
	public List<Appointment> getAppointmentsForTodayByDoctorIdAndStatus(long doctorId, String status)
			throws RecordNotFoundException {
		LocalDate currentDate = LocalDate.now();

        // Call the custom repository method to fetch appointments for today by doctorId and status
        List<Appointment> todayAppointments = repo.findByDateAndDoctorIdAndStatus(currentDate, doctorId, status);

        if (todayAppointments.isEmpty()) {
        	throw new RecordNotFoundException("No today's appointments found for Doctor ID: " + doctorId);
        }

        return todayAppointments;
	}

	

	
}

