package com.oneHealth.Appointments.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.AppointmentNotFoundException;
import com.oneHealth.Appointments.exception.DatabaseException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;
import com.oneHealth.Appointments.service.AppointmentService;
import java.util.HashMap;

/**
 * The main class to start the OneHealth Doctor Appointment Services
 * application.
 * 
 * @author Anup
 * @version 1.0
 */
@RestController
@RequestMapping("/api/doctors/appointment")
public class AppointmentController {

	private static final Logger LOGGER = Logger.getLogger(AppointmentController.class.getName());

	@Autowired
	private AppointmentService service;

	/**
	 * Saves the details of a new appointment.
	 *
	 * @param appointment The Appointment object containing the details to be saved.
	 * @return ResponseEntity<String> A response indicating the success of the
	 *         operation.
	 * @throws Exception
	 */
	@PostMapping("/saveappointment")
	public ResponseEntity<String> saveAppointmentDetails(@RequestBody Appointment appointment) {
		LOGGER.info("In Controller - Saving appointment details: " + appointment);
		try {
			service.saveDoctorAppointment(appointment);
		} catch (Exception e) {
			return new ResponseEntity<>("Appointment Slot Already Booked", HttpStatus.CONFLICT);
		}
		try {
			service.savePatientAppointment(appointment);
		} catch (Exception e) {
			return new ResponseEntity<>("Appointment Slot Already Booked", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>("Appointment Saved Successfully", HttpStatus.CREATED);
	}

	

	/**
	 * Retrieves an appointment by its ID.
	 *
	 * @param appointment_id The ID of the appointment to retrieve.
	 * @return ResponseEntity<Appointment> A response entity containing the appointment with the specified ID,
	 *                                    or a 404 Not Found response if the appointment does not exist.
	 */
//	@GetMapping("/getAppointment/{appointment_id}")
//	public ResponseEntity<Appointment> getAppointmentById(@PathVariable(value = "appointment_id") int appointment_id) {
//	    try {
//	        Appointment appointment = service.getAppointmentById(appointment_id);
//
//	        if (appointment != null) {
//	            // Return a 200 OK response with the appointment object
//	            return ResponseEntity.ok(appointment);
//	        } else {
//	            // Return a 404 Not Found response if the appointment with the given ID does not exist
//	            return ResponseEntity.notFound().build();
//	        }
//	    } catch (Exception ex) {
//	        // Handle any unexpected exceptions here
//	        // Log the error for debugging purposes
//	        LOGGER.info("An unexpected error occurred: " + ex.getMessage());
//	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//	
	

	@GetMapping("/getAppointment/{appointment_id}")
	public ResponseEntity<?> getAppointmentById(@PathVariable(value = "appointment_id") int appointment_id) throws AppointmentNotFoundException {
	    try {
	        Appointment appointment = service.getAppointmentById(appointment_id);

	        if (appointment != null) {
	            // Return a 200 OK response with the appointment object
	            return ResponseEntity.ok(appointment);
	        } else {
	            // Return a 404 Not Found response with a custom message
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Appointment not found for ID: " + appointment_id);
	        }
	    } catch (Exception ex) {
	        // Handle any unexpected exceptions here
	        // Log the error for debugging purposes
	        LOGGER.info("An unexpected error occurred: " + ex.getMessage());

	        // Return a 500 Internal Server Error response with a custom message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("An unexpected error occurred while retrieving the appointment.");
	    }
	}


	
	@GetMapping("/getAllAppointments")
	public ResponseEntity<List<Appointment>> getAllAppointments() {
	    try {
	        List<Appointment> appointmentList = service.getAllAppointments(); // Replace 'Appointment' with your actual appointment entity class
	        LOGGER.info("In Controller - All Appointments Retrieved: " + appointmentList);
	        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
	    } catch (DatabaseException ex) {
	        // Handle DatabaseException here
	    	LOGGER.info("Database error: " + ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } catch (Exception ex) {
	        // Handle other exceptions here
	    	LOGGER.info("An error occurred: " + ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	/**
	 * Retrieves a list of appointments for a specific doctor with the status
	 * "Accepted".
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
	@GetMapping("/doctor/{doctorId}/Accepted")
	public ResponseEntity<?> getAppointmentsByDoctorIdAndAccepted(
	        @PathVariable("doctorId") long doctorId) {
	    try {
	        LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Accepted'");
	        String status = "Accepted";
	        List<Appointment> appointments = service.findByDoctorIdAndStatus(doctorId, status);
	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Accepted'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Accepted': "
	                    + appointments);
	            return ResponseEntity.ok().body("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Accepted': ");
	        }
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while retrieving appointments for doctor ID: " + doctorId);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving appointments for doctor ID: " + doctorId);
	    }
	}


	/**
	 * Retrieves a list of appointments for a specific doctor with the status "Not
	 * Accepted".
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
//	
	@GetMapping("/doctor/{doctorId}/NotAccepted")
	public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndNotAccepted(
	        @PathVariable("doctorId") long doctorId) {
	    LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Not Accepted'");
	    String status = "Not Accepted";
	    List<Appointment> appointments;

	    try {
	        appointments = service.NotAcceptedAppointmentsForRequest(doctorId, status);
	        
	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Not Accepted'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Not Accepted': " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while retrieving appointments for doctor ID: " + doctorId);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	


	/**
	 * Retrieves a list of appointments for a specific doctor with the status "Not
	 * Accepted".
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
//
	@GetMapping("/doctor/{doctorId}/Completed")
	public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndCompleted(
	        @PathVariable("doctorId") long doctorId) {
	    LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Completed'");
	    String status = "Completed";
	    List<Appointment> appointments;

	    try {
	        appointments = service.findByDoctorIdAndStatus(doctorId, status);

	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Completed'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Completed': " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while retrieving appointments for doctor ID: " + doctorId);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	/**
	 * Retrieves a list of appointments for a specific doctor with the status "Not
	 * Accepted".
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
//	

	@GetMapping("/doctor/{doctorId}/Rejected")
	public ResponseEntity<?> getAppointmentsByDoctorIdAndRejected(
	        @PathVariable("doctorId") long doctorId) {
	    try {
	        LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Rejected'");
	        String status = "Rejected";
	        List<Appointment> appointments = service.findByDoctorIdAndStatus(doctorId, status);
	        
	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Rejected'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId
	                    + " with status 'Rejected': " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while retrieving appointments for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
	    }
	}

	/**
	 * Updates the status of an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be updated.
	 * @param status         The new status for the appointment.
	 * @return ResponseEntity<String> A response indicating the success of the
	 *         update operation.
	 * @throws AppointmentNotFoundException If no appointment is found with the given
	 *                                  ID.
	 */
	@PutMapping("updateappointment/{appointment_id}/update/{status}")
	public ResponseEntity<String> updateAppointmentStatus(@PathVariable("appointment_id") long appointment_id,
	        @PathVariable("status") String status) {
	    try {
	        LOGGER.info("In Controller - Updating appointment status for ID: " + appointment_id + " to: " + status);
	        service.updateAppointmentStatus(appointment_id, status);
	        return new ResponseEntity<>("Status Updated Successfully", HttpStatus.OK);
	    } catch (AppointmentNotFoundException e) {
	        LOGGER.warning("ProfileNotFoundException occurred while updating appointment status for ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Profile not found.");
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while updating appointment status for ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Updates the date and time of an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be updated.
	 * @param newDate        The new date for the appointment.
	 * @param newTime        The new time for the appointment.
	 * @return ResponseEntity<String> A response indicating the success of the
	 *         update operation.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@PutMapping("/update-date-time/{id}")
	public ResponseEntity<String> updateAppointmentDateTime(@PathVariable(value = "id") long appointment_id,
	        @RequestParam("newDate") Date newDate, @RequestParam("newTime") Time newTime) {
	    try {
	        LOGGER.info("In Controller - Updating appointment date and time for ID: " + appointment_id + " - New date: "
	                + newDate + ", New time: " + newTime);
	        service.updateAppointmentDateTime(appointment_id, newDate, newTime);
	        return new ResponseEntity<>("Appointment Date and Time Updated Successfully", HttpStatus.OK);
	    } catch (RecordNotFoundException e) {
	        LOGGER.warning("RecordNotFoundException occurred while updating appointment date and time for ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while updating appointment date and time for ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Deletes an appointment with the given appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be deleted.
	 * @return ResponseEntity<String> A response indicating the success of the
	 *         delete operation.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@DeleteMapping("/delete-appointment/{id}")
	public ResponseEntity<?> deleteAppointment(@PathVariable(value = "id") long appointment_id) {
	    try {
	        LOGGER.info("In Controller - Deleting appointment with ID: " + appointment_id);
	        service.deleteAppointment(appointment_id);
	        
	        return ResponseEntity.ok("Appointment Deleted Successfully");
	    } catch (RecordNotFoundException e) {
	        LOGGER.info("RecordNotFoundException occurred while deleting appointment with ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.warning("An error occurred while deleting appointment with ID: " + appointment_id);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Retrieves a list of appointments scheduled for today.
	 *
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
	@GetMapping("/appointments-for-today")
	public ResponseEntity<?> getAppointmentsForToday() {
	    try {
	        LOGGER.info("In Controller - Retrieving appointments for today");
	        List<Appointment> appointments = service.getAppointmentsForToday();
	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for today");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for today: " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while retrieving appointments for today");
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Retrieves a list of upcoming appointments for a specific doctor by their ID,
	 * status, and appointment type.
	 *
	 * @param doctorId The ID of the doctor for whom upcoming appointments are
	 *                 retrieved.
	 * @param type     The type of appointments to be retrieved.
	 * @return ResponseEntity<List<Appointment>> A ResponseEntity containing the
	 *         list of upcoming appointments for the specified doctor.
	 * @throws RecordNotFoundException If no upcoming appointments are found with
	 *                                 the specified status, doctor ID, and type.
	 */
	@GetMapping("/upcoming-appointments/doctorId/{doctorId}/type/{type}/status/{status}")
	public ResponseEntity<?> getTodaysAppointmentsByDoctorIdAndStatusAndType(
	        @PathVariable long doctorId, @PathVariable String type, @PathVariable String status) {
	    try {
	        LOGGER.info("In Controller - Retrieving upcoming appointments for doctor ID: " + doctorId
	                + " with status 'Accepted' and type: " + type);
	        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatusAndType(doctorId,
	                type, status);
	        if (upcomingAppointments.isEmpty()) {
	            LOGGER.info("In Controller - No upcoming appointments found for doctor ID: " + doctorId
	                    + " with status 'Accepted' and type: " + type);
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Upcoming appointments found for doctor ID: " + doctorId
	                    + " with status 'Accepted' and type: " + type + ": " + upcomingAppointments);
	            return ResponseEntity.ok(upcomingAppointments);
	        }
	    } catch (RecordNotFoundException e) {
	        LOGGER.info("RecordNotFoundException occurred while retrieving upcoming appointments for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while retrieving upcoming appointments for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}

	/**
	 * Retrieves a list of upcoming appointments with the status "Accepted".
	 *
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
	@GetMapping("/upcoming")
	public ResponseEntity<?> getUpcomingAppointmentsWithStatus() {
	    try {
	        LOGGER.info("In Controller - Retrieving upcoming appointments with status 'Accepted'");
	        String status = "Accepted";
	        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsWithStatus(status);
	        LOGGER.info("In Controller - Upcoming appointments with status 'Accepted': " + upcomingAppointments);
	        return ResponseEntity.ok(upcomingAppointments);
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while retrieving upcoming appointments with status 'Accepted'");
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Retrieves a list of accepted appointments for today for a specific doctor by
	 * their ID.
	 *
	 * @param doctorId The ID of the doctor for whom today's accepted appointments
	 *                 are retrieved.
	 * @return ResponseEntity<List<Appointment>> A ResponseEntity containing the
	 *         list of today's accepted appointments for the specified doctor.
	 * @throws RecordNotFoundException If no appointments are found for today with
	 *                                 the specified status and doctor ID.
	 */
	@GetMapping("/appointments-for-today/doctor/{doctorId}")
	public ResponseEntity<?> getAppointmentsForTodayByDoctorIdAndStatus(@PathVariable long doctorId) {
	    try {
	        LOGGER.info("In Controller - Retrieving appointments for today for doctor ID: " + doctorId
	                + " with status 'Accepted'");
	        String status = "Accepted";
	        List<Appointment> todayAppointments = service.getAppointmentsForTodayByDoctorIdAndStatus(doctorId, status);
	        if (todayAppointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for today for doctor ID: " + doctorId
	                    + " with status 'Accepted'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for today for doctor ID: " + doctorId
	                    + " with status 'Accepted': " + todayAppointments);
	            return ResponseEntity.ok(todayAppointments);
	        }
	    } catch (RecordNotFoundException e) {
	        LOGGER.info("RecordNotFoundException occurred while retrieving appointments for today for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while retrieving appointments for today for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}

	/**
	 * Retrieves a list of upcoming appointments for a specific doctor with the
	 * status "Accepted".
	 *
	 * @param doctorId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 * @throws RecordNotFoundException If no appointments are found for the given
	 *                                 doctor ID.
	 */
	@GetMapping("/upcoming-appointments/doctor/{doctorId}")
	public ResponseEntity<?> getUpcomingAppointmentsByDoctorIdAndStatus(@PathVariable long doctorId) {
	    try {
	        LOGGER.info("In Controller - Retrieving upcoming appointments for doctor ID: " + doctorId
	                + " with status 'Accepted'");
	        String status = "Accepted";
	        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
	        if (upcomingAppointments.isEmpty()) {
	            LOGGER.info("In Controller - No upcoming appointments found for doctor ID: " + doctorId
	                    + " with status 'Accepted'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Upcoming appointments found for doctor ID: " + doctorId
	                    + " with status 'Accepted': " + upcomingAppointments);
	            return ResponseEntity.ok(upcomingAppointments);
	        }
	    } catch (RecordNotFoundException e) {
	        LOGGER.info("RecordNotFoundException occurred while retrieving upcoming appointments for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while retrieving upcoming appointments for doctor ID: " + doctorId);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Updates the details of an appointment with the given appointment ID.
	 *
	 * @param appointmentId      The ID of the appointment to be updated.
	 * @param updatedAppointment The updated Appointment object.
	 * @return ResponseEntity<String> A response indicating the success of the
	 *         update operation.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@PutMapping("/update-appointment/{id}")
	public ResponseEntity<String> updateAppointment(@PathVariable(value = "id") long appointmentId,
	        @RequestBody Appointment updatedAppointment) {
	    try {
	        LOGGER.info("In Controller - Updating appointment with ID: " + appointmentId + " to: " + updatedAppointment);
	        service.updateAppointment(appointmentId, updatedAppointment);
	        return ResponseEntity.ok("Appointment Updated Successfully");
	    } catch (RecordNotFoundException e) {
	        LOGGER.info("RecordNotFoundException occurred while updating appointment with ID: " + appointmentId);
	        
	        // Return a ResponseEntity with a custom error message and a 404 Not Found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Record not found.");
	    } catch (Exception e) {
	        LOGGER.info("An error occurred while updating appointment with ID: " + appointmentId);
	        
	        // Return a ResponseEntity with a custom error message and a 500 Internal Server Error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request.");
	    }
	}


	/**
	 * Retrieves the count of today's accepted appointments for a specific doctor.
	 *
	 * @param doctorId The ID of the doctor for whom appointments are counted.
	 * @return ResponseEntity<Long> A ResponseEntity containing the count of today's
	 *         accepted appointments for the specified doctor.
	 */
	@GetMapping("/count/{doctorId}")
	public ResponseEntity<Long> getTodayAppointmentsCountByDoctorAndStatus(@PathVariable long doctorId) {
	    String status = "Accepted";
	    try {
	        long count = service.getTodayAppointmentsCountByDoctorIdAndStatus(doctorId, status);
	        return ResponseEntity.ok(count);
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error response
	    }
	}


	/**
	 * Retrieves the count of upcoming accepted appointments for a specific doctor.
	 *
	 * @param doctorId The ID of the doctor for whom appointments are counted.
	 * @return ResponseEntity<Long> A ResponseEntity containing the count of
	 *         upcoming accepted appointments for the specified doctor.
	 */
	@GetMapping("/count/upcoming/{doctorId}")
	public ResponseEntity<Long> getCountOfUpcomingAppointmentsByDoctorIdAndStatus(@PathVariable Long doctorId) {
	    String status = "Accepted";
	    try {
	        Long count = service.getCountOfUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
	        return new ResponseEntity<>(count, HttpStatus.OK);
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error response
	    }
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Retrieves a list of completed appointments for a specific patient.
	 *
	 * @param patientId The ID of the patient for whom completed appointments are
	 *                  retrieved.
	 * @return List<Appointment> A list of completed appointments for the specified
	 *         patient.
	 */
	@GetMapping("/patient/{patientId}/completed")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndCompleted(@PathVariable long patientId) {
	    String status = "Completed";
	    try {
	        List<Appointment> appointments = service.findByPatientIdAndStatus(patientId, status);
	        return ResponseEntity.ok(appointments);
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error response
	    }
	}


	/**
	 * Retrieves a list of rejected appointments for a specific patient.
	 *
	 * @param patientId The ID of the patient for whom rejected appointments are
	 *                  retrieved.
	 * @return List<Appointment> A list of rejected appointments for the specified
	 *         patient.
	 */
	@GetMapping("/patient/{patientId}/rejected")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndRejected(@PathVariable long patientId) {
	    String status = "Rejected";
	    try {
	        List<Appointment> appointments = service.findByPatientIdAndStatus(patientId, status);
	        return ResponseEntity.ok(appointments);
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error response
	    }
	}


	/**
	 * Retrieves a list of not accepted appointments for a specific patient.
	 *
	 * @param patientId The ID of the patient for whom not accepted appointments are
	 *                  retrieved.
	 * @return List<Appointment> A list of not accepted appointments for the
	 *         specified patient.
	 */
	@GetMapping("/patient/{patientId}/NotAccepted")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndNotAccepted(@PathVariable long patientId) {
	    String status = "Not Accepted";
	    try {
	        List<Appointment> appointments = service.findByPatientIdAndStatus(patientId, status);
	        return ResponseEntity.ok(appointments);
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error response
	    }
	}


	/**
	 * Retrieves a list of upcoming appointments for a specific doctor with the
	 * status "Accepted".
	 *
	 * @param patientId The ID of the doctor for whom to retrieve the appointments.
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 * @throws RecordNotFoundException If no appointments are found for the given
	 *                                 doctor ID.
	 */
	@GetMapping("/upcoming-appointments/patient/{patientId}")
	public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByPatientIdAndStatus(@PathVariable long patientId) {
	    LOGGER.info("In Controller - Retrieving upcoming appointments for patient ID: " + patientId + " with status 'Accepted'");
	    String status = "Accepted";
	    
	    try {
	        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByPatientIdAndStatus(patientId, status);
	        List<Appointment> todayappointments = service.getAppointmentsForTodayByPatientIdAndStatus(patientId, status);
	        List<Appointment> FinalUpcoming = new ArrayList<>();
	        FinalUpcoming.addAll(upcomingAppointments);
	        FinalUpcoming.addAll(todayappointments);

	        if (FinalUpcoming.isEmpty()) {
	            LOGGER.info("In Controller - No upcoming appointments found for patient ID: " + patientId + " with status 'Accepted'");
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Upcoming appointments found for patient ID: " + patientId + " with status 'Accepted': " + upcomingAppointments);
	            return ResponseEntity.ok(FinalUpcoming);
	        }
	    } catch (RecordNotFoundException e) {
	        // Handle the specific exception (RecordNotFoundException) appropriately
	        LOGGER.info("Record not found: " + e.getMessage());
	        return ResponseEntity.notFound().build(); // Return a 404 Not Found response or handle it based on your application's error handling strategy.
	    } catch (Exception e) {
	        // Handle other exceptions
	        LOGGER.info("An error occurred: " + e.getMessage());
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return a 500 Internal Server Error response or handle it based on your application's error handling strategy.
	    }
	}

	/**
	 * Retrieves a list of appointments for a specific patient with the given
	 * patient ID and type.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the appointments.
	 * @param type      The type of appointments to retrieve (e.g., "checkup",
	 *                  "follow-up", etc.).
	 * @return ResponseEntity<List<Appointment>> A response containing a list of
	 *         appointments.
	 */
	@GetMapping("/patient/{patientId}/type/{type}")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndType(
	        @PathVariable("patientId") long patientId, @PathVariable("type") String type) {
	    LOGGER.info("In Controller - Retrieving appointments for patient ID: " + patientId + " with type: " + type);
	    
	    try {
	        List<Appointment> appointments = service.findByPatientIdAndType(patientId, type);
	        
	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for patient ID: " + patientId + " with type: " + type);
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for patient ID: " + patientId + " with type: " + type + ": " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        LOGGER.info("An error occurred: " + e.getMessage());
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return a 500 Internal Server Error response or handle it based on your application's error handling strategy.
	    }
	}


	/**
	 * Retrieves a list of upcoming appointments for a specific patient by their ID.
	 *
	 * @param patientId The ID of the patient for whom upcoming appointments are
	 *                  retrieved.
	 * @return ResponseEntity<List<Appointment>> A ResponseEntity containing the
	 *         list of upcoming appointments for the specified patient.
	 */
	@GetMapping("/upcoming-for-patients-all/{patientId}")
	public ResponseEntity<List<Appointment>> getUpcomingByPatientId(@PathVariable("patientId") long patientId) {
	    try {
	        List<Appointment> appointments = service.findUpcomingByPatientId(patientId);

	        if (appointments.isEmpty()) {
	            LOGGER.info("In Controller - No appointments found for patient ID: " + patientId);
	            return ResponseEntity.noContent().build();
	        } else {
	            LOGGER.info("In Controller - Appointments found for patient ID: " + patientId + ": " + appointments);
	            return ResponseEntity.ok(appointments);
	        }
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or return an error response
	        LOGGER.info("An error occurred: " + e.getMessage());
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return a 500 Internal Server Error response or handle it based on your application's error handling strategy.
	    }
	}


	/**
	 * Retrieves a list of appointment times for available slots on a specific date
	 * and for a specific doctor.
	 *
	 * @param doctorId The ID of the doctor for whom available slots are retrieved.
	 * @param date     The date for which available slots are retrieved.
	 * @return List<Time> A list of appointment times for available slots.
	 */
	@GetMapping("/appointment-times-for-slots")
	public List<Time> getAppointmentTimes(@RequestParam("doctorId") long doctorId, @RequestParam("date") Date date) {
	    try {
	        // Assuming you have a method in your service to fetch appointment times
	        List<Appointment> appointments = service.getAppointmentTimeForSlots(doctorId, date);

	        // Extract appointment times from the list of appointments
	        List<Time> appointmentTimes = appointments.stream().map(Appointment::getAppointmentTime)
	                .collect(Collectors.toList());

	        return appointmentTimes;
	    } catch (Exception e) {
	        // Handle the exception appropriately, you can log it or perform other error handling actions.
	        e.printStackTrace(); // This is just an example, consider using a proper logging framework.
	        return Collections.emptyList(); // Return an empty list or another appropriate response for the error.
	    }
	}


}
