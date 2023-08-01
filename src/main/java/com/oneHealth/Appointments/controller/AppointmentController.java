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
	
	
	/**
	 * Retrieves the list of appointments for a specific patient with the given patient ID.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing the list of appointments with HTTP status 200 (OK).
	 */
	@PostMapping("/saveappointment")
	public ResponseEntity<String> saveAppointmentDetails(@RequestBody Appointment appointment)
	{
		service.saveAppointment(appointment);
		return new ResponseEntity<>("Appointment Saved Successfully",HttpStatus.OK);
	}
	
	
	
	/**
	 * Retrieves the list of appointments for a specific patient with the given patient ID.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing the list of appointments with HTTP status 200 (OK).
	 */
	@GetMapping("/findbypatientid/{id}")
	public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable(value="id") long patientId)
	{
		List<Appointment> obj = service.findByPatientId(patientId);
		return ResponseEntity.ok().body(obj);
	}
	
	
	
	
	/**
	 * Retrieves the list of appointments for a specific doctor with the given doctor ID.
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing the list of appointments with HTTP status 200 (OK).
	 */
	@GetMapping("/findbydoctorid/{id}")
	public ResponseEntity<List<Appointment>> findByDoctorId(@PathVariable(value="id") long doctorId)
	{
		List<Appointment> obj = service.findByDoctorId(doctorId);
		return ResponseEntity.ok().body(obj);
	}
	
	
	/**
	 * Retrieves the list of appointments for a specific doctor with the given doctor ID and status.
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @param status The status of the appointments to retrieve (e.g., "Accepted", "Pending", etc.).
	 * @return ResponseEntity<List<Appointment>> A response containing the list of appointments with HTTP status 200 (OK) if found, or HTTP status 204 (NO_CONTENT) if no appointments are found.
	 */
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
	
	/**
	 * Retrieves the list of appointments for a specific patient with the given patient ID and type.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @param type The type of appointments to retrieve (e.g., "checkup", "follow-up", etc.).
	 * @return ResponseEntity<List<Appointment>> A response containing the list of appointments with HTTP status 200 (OK) if found, or HTTP status 204 (NO_CONTENT) if no appointments are found.
	 */
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
	
	
	/**
	 * Updates the status of an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be updated.
	 * @param status The new status for the appointment.
	 * @return ResponseEntity<String> A response indicating that the appointment status has been updated successfully.
	 * @throws ProfileNotFoundException If no appointment is found with the given ID.
	 */
	@PutMapping("updateappointment/{appointment_id}/update/{status}")
	public ResponseEntity<String> updateAppointmentStatus(@PathVariable("appointment_id") long appointment_id , @PathVariable("status") String status) throws ProfileNotFoundException
	{
		service.updateAppointmentStatus(appointment_id, status);
		return new ResponseEntity<>("Status Updated Successfully",HttpStatus.OK);
	}
	
	
	/**
	 * Updates the date and time of an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be updated.
	 * @param newDate The new date for the appointment.
	 * @param newTime The new time for the appointment.
	 * @return ResponseEntity<String> A response indicating that the appointment date and time have been updated successfully.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@PutMapping("/update-date-time/{id}")
	public ResponseEntity<String> updateAppointmentDateTime(
	        @PathVariable(value = "id") long appointment_id,
	        @RequestParam("newDate") Date newDate,
	        @RequestParam("newTime") Time newTime) throws RecordNotFoundException {

	    service.updateAppointmentDateTime(appointment_id, newDate, newTime);
	    return new ResponseEntity<>("Appointment Date and Time Updated Successfully", HttpStatus.OK);
	}
	
	
	/**
	 * Deletes an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be deleted.
	 * @return ResponseEntity<String> A response indicating that the appointment has been deleted successfully.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@DeleteMapping("/delete-appointment/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable(value = "id") long appointment_id) throws RecordNotFoundException {
	    service.deleteAppointment(appointment_id);
	    return new ResponseEntity<>("Appointment Deleted Successfully", HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Retrieves all the appointments scheduled for today's date.
	 *
	 * @return ResponseEntity<List<Appointment>> The list of appointments scheduled for today's date.
	 */
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

	
	
	
	/**
	 * Retrieves all the upcoming appointments with a specific status.
	 *
	 * @return ResponseEntity<List<Appointment>> The list of upcoming appointments with the specified status.
	 */
	@GetMapping("/upcoming")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsWithStatus() {
	    String status = "Accepted";
	    // Retrieve and return upcoming appointments with status "Accepted" from the service layer.
	    List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsWithStatus(status);
	    return ResponseEntity.ok(upcomingAppointments);
	}

	
	
	
	/**
	 * Retrieves all the upcoming appointments with a specific status for a particular doctor based on the doctorId.
	 *
	 * @param doctorId The ID of the doctor for whom upcoming appointments need to be fetched.
	 * @return ResponseEntity<List<Appointment>> The list of upcoming appointments for the specified doctor.
	 * @throws RecordNotFoundException If no upcoming appointments are found for the given doctor and status.
	 */
	@GetMapping("/upcoming-appointments/doctor/{doctorId}")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorIdAndStatus(
	        @PathVariable long doctorId) throws RecordNotFoundException 
	{
	    String status = "Accepted";
	    // Retrieve and return upcoming appointments with status "Accepted" for a specific doctor by doctorId from the service layer.
	    List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
	    return ResponseEntity.ok(upcomingAppointments);
	}

	
	
	/**
	 * Retrieves all the upcoming appointments with a specific status and type for a particular doctor based on the doctorId and type.
	 *
	 * @param doctorId The ID of the doctor for whom upcoming appointments need to be fetched.
	 * @param type     The type of appointment for which upcoming appointments need to be fetched.
	 * @return ResponseEntity<List<Appointment>> The list of upcoming appointments for the specified doctor and type.
	 * @throws RecordNotFoundException If no upcoming appointments are found for the given doctor, status, and type.
	 */
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
	
	
	
	/**
	 * Retrieves all the appointments scheduled for today for a specific doctor based on the doctorId.
	 *
	 * @param doctorId The ID of the doctor for whom appointments need to be fetched.
	 * @return ResponseEntity<List<Appointment>> The list of appointments for today.
	 * @throws RecordNotFoundException If no appointments are found for the given doctor and status.
	 */
	@GetMapping("/appointments-for-today/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForTodayByDoctorIdAndStatus(
            @PathVariable long doctorId) throws RecordNotFoundException 
	{

		String status = "Accepted";
        List<Appointment> todayAppointments = service.getAppointmentsForTodayByDoctorIdAndStatus(doctorId, status);

        if (todayAppointments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 (NO_CONTENT) if no appointments found for today.
        } else {
            return ResponseEntity.ok(todayAppointments); // Return appointments with status 200 (OK).
        }
    }

}
