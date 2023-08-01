package com.oneHealth.Appointments.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.ProfileNotFoundException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;
import com.oneHealth.Appointments.service.AppointmentService;


/**
 * Controller class that handles HTTP requests and responses related to Appointments.
 * @author Anup
 * @version 3.9.10
 */
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController 
{
	
	@Autowired
	private AppointmentService service;
	
	
	// Save appointment details to the database.
	@PostMapping("/saveappointment")
	public ResponseEntity<String> saveAppointmentDetails(@RequestBody Appointment appointment)
	{
		service.saveAppointment(appointment);
		return new ResponseEntity<>("Appointment Saved Successfully",HttpStatus.OK);
	}
	
	// Find an appointment based on the patient ID
	@GetMapping("/findbypatientid/{id}")
	public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable(value="id") long patientId)
	{
		List<Appointment> obj = service.findByPatientId(patientId);
		return ResponseEntity.ok().body(obj);
	}
	
	
	// Find an appointment based on the doctor ID
	@GetMapping("/findbydoctorid/{id}")
	public ResponseEntity<List<Appointment>> findByDoctorId(@PathVariable(value="id") long doctorId)
	{
		List<Appointment> obj = service.findByDoctorId(doctorId);
		return ResponseEntity.ok().body(obj);
	}
	
	
	// Find an appointment based on the doctor ID as well as the appointment status
	@GetMapping("/doctor/{doctorId}/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndStatus(
            @PathVariable("doctorId") long doctorId,
            @PathVariable("status") String status) {
        
        List<Appointment> appointments = service.findByDoctorIdAndStatus(doctorId, status);
        
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 (NO_CONTENT) if no appointments found.
        } else {
            return ResponseEntity.ok(appointments); // Return appointments with status 200 (OK).
        }
    }
	
	// Get appointments by patient ID and type.
	@GetMapping("/patient/{patientId}/type/{type}")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndType(@PathVariable("patientId") long patientId,
            @PathVariable("type") String type)
	{
		List<Appointment> appointments = service.findByPatientIdAndType(patientId, type);
        
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 (NO_CONTENT) if no appointments found.
        } else {
            return ResponseEntity.ok(appointments); // Return appointments with status 200 (OK).
        }
	}
	
	
	// Update the status of an appointment.
	@PutMapping("updateappointment/{appointment_id}/update/{status}")
	public ResponseEntity<String> updateAppointmentStatus(@PathVariable("appointment_id") long appointment_id , @PathVariable("status") String status) throws ProfileNotFoundException
	{
		service.updateAppointmentStatus(appointment_id, status);
		return new ResponseEntity<>("Status Updated Successfully",HttpStatus.OK);
	}
	
	
	// Update the date and time of an appointment.
	@PutMapping("/update-date-time/{id}")
	public ResponseEntity<String> updateAppointmentDateTime(
	        @PathVariable(value = "id") long appointment_id,
	        @RequestParam("newDate") Date newDate,
	        @RequestParam("newTime") Time newTime) throws RecordNotFoundException {

	    service.updateAppointmentDateTime(appointment_id, newDate, newTime);
	    return new ResponseEntity<>("Appointment Date and Time Updated Successfully", HttpStatus.OK);
	}
	
	
	// Delete an appointment by its ID.
	@DeleteMapping("/delete-appointment/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable(value = "id") long appointment_id) throws RecordNotFoundException {
	    service.deleteAppointment(appointment_id);
	    return new ResponseEntity<>("Appointment Deleted Successfully", HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping("/appointments-for-today")
	public ResponseEntity<List<Appointment>> getAppointmentsForToday() {
	    // Retrieve and return appointments for today's date from the service layer.
	    List<Appointment> appointments = service.getAppointmentsForToday();
	    
	    if (appointments.isEmpty()) {
	        // If no appointments found for today, return a response with HTTP status 204 (NO_CONTENT).
	        // The 204 status code indicates that the server successfully processed the request but there is no content to send in the response.
	        return ResponseEntity.noContent().build();
	    } else {
	        // If appointments found, return the list of appointments in the response with HTTP status 200 (OK).
	        return ResponseEntity.ok(appointments);
	    }
	}

	
	
	
	
	@GetMapping("/upcoming")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsWithStatus() {
	    String status = "Accepted";
	    // Retrieve and return upcoming appointments with status "Accepted" from the service layer.
	    List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsWithStatus(status);
	    return ResponseEntity.ok(upcomingAppointments);
	}

	
	
	
	
	@GetMapping("/upcoming-appointments/doctor/{doctorId}")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorIdAndStatus(
	        @PathVariable long doctorId) throws RecordNotFoundException 
	{
	    String status = "Accepted";
	    // Retrieve and return upcoming appointments with status "Accepted" for a specific doctor by doctorId from the service layer.
	    List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
	    return ResponseEntity.ok(upcomingAppointments);
	}

	
	
	
	@GetMapping("/upcoming-appointments/doctor/{doctorId}/type/{type}")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorIdAndStatusAndType(
	        @PathVariable long doctorId,
	        @PathVariable String type) throws RecordNotFoundException 
	{
	    String status = "Accepted";
	    // Retrieve and return upcoming appointments with status "Accepted" and a specific type for a specific doctor by doctorId from the service layer.
	    List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatusAndType(doctorId, status, type);
	    return ResponseEntity.ok(upcomingAppointments);
	}

}
