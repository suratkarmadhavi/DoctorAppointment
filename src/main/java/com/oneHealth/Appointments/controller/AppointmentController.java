package com.oneHealth.Appointments.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin("*")
@RequestMapping("/api/appointment")
public class AppointmentController {

    private static final Logger LOGGER = Logger.getLogger(AppointmentController.class.getName());

    @Autowired
    private AppointmentService service;

    /**
     * Saves the details of a new appointment.
     *
     * @param appointment The Appointment object containing the details to be saved.
     * @return ResponseEntity<String> A response indicating the success of the operation.
     */
    @PostMapping("/saveappointment")
    public ResponseEntity<String> saveAppointmentDetails(@RequestBody Appointment appointment) {
        LOGGER.info("In Controller - Saving appointment details: " + appointment);
        service.saveAppointment(appointment);
        return new ResponseEntity<>("Appointment Saved Successfully", HttpStatus.OK);
    }

    /**
     * Retrieves a list of appointments for a specific patient.
     *
     * @param patientId The ID of the patient for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/findbypatientid/{id}")
    public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable(value = "id") long patientId) {
        LOGGER.info("In Controller - Retrieving appointments for patient ID: " + patientId);
        List<Appointment> obj = service.findByPatientId(patientId);
        return ResponseEntity.ok().body(obj);
    }

    /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @param doctorId The ID of the doctor for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/findbydoctorid/{id}")
    public ResponseEntity<List<Appointment>> findByDoctorId(@PathVariable(value = "id") long doctorId) {
        LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId);
        List<Appointment> obj = service.findByDoctorId(doctorId);
        return ResponseEntity.ok().body(obj);
    }

    /**
     * Retrieves a list of appointments for a specific doctor with the status "Accepted".
     *
     * @param doctorId The ID of the doctor for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/doctor/{doctorId}/Accepted")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndAccepted(
            @PathVariable("doctorId") long doctorId) {
        LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> appointments = service.findByDoctorIdAndStatus(doctorId, status);
        if (appointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Accepted': " + appointments);
            return ResponseEntity.ok(appointments);
        }
    }

    /**
     * Retrieves a list of appointments for a specific doctor with the status "Not Accepted".
     *
     * @param doctorId The ID of the doctor for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/doctor/{doctorId}/NotAccepted")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndNotAccepted(
            @PathVariable("doctorId") long doctorId) {
        LOGGER.info("In Controller - Retrieving appointments for doctor ID: " + doctorId + " with status 'Not Accepted'");
        String status = "Not Accepted";
        List<Appointment> appointments = service.findByDoctorIdAndStatus(doctorId, status);
        if (appointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for doctor ID: " + doctorId + " with status 'Not Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for doctor ID: " + doctorId + " with status 'Not Accepted': " + appointments);
            return ResponseEntity.ok(appointments);
        }
    }

    /**
     * Retrieves a list of appointments for a specific patient with the given patient ID and type.
     *
     * @param patientId The ID of the patient for whom to retrieve the appointments.
     * @param type The type of appointments to retrieve (e.g., "checkup", "follow-up", etc.).
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientIdAndType(
            @PathVariable("patientId") long patientId,
            @PathVariable("type") String type) {
        LOGGER.info("In Controller - Retrieving appointments for patient ID: " + patientId + " with type: " + type);
        List<Appointment> appointments = service.findByPatientIdAndType(patientId, type);
        if (appointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for patient ID: " + patientId + " with type: " + type);
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for patient ID: " + patientId + " with type: " + type + ": " + appointments);
            return ResponseEntity.ok(appointments);
        }
    }

    /**
     * Updates the status of an appointment with the given appointment ID.
     *
     * @param appointment_id The ID of the appointment to be updated.
     * @param status The new status for the appointment.
     * @return ResponseEntity<String> A response indicating the success of the update operation.
     * @throws ProfileNotFoundException If no appointment is found with the given ID.
     */
    @PutMapping("updateappointment/{appointment_id}/update/{status}")
    public ResponseEntity<String> updateAppointmentStatus(
            @PathVariable("appointment_id") long appointment_id,
            @PathVariable("status") String status) throws ProfileNotFoundException {
        LOGGER.info("In Controller - Updating appointment status for ID: " + appointment_id + " to: " + status);
        service.updateAppointmentStatus(appointment_id, status);
        return new ResponseEntity<>("Status Updated Successfully", HttpStatus.OK);
    }

    /**
     * Updates the date and time of an appointment with the given appointment ID.
     *
     * @param appointment_id The ID of the appointment to be updated.
     * @param newDate The new date for the appointment.
     * @param newTime The new time for the appointment.
     * @return ResponseEntity<String> A response indicating the success of the update operation.
     * @throws RecordNotFoundException If no appointment is found with the given ID.
     */
    @PutMapping("/update-date-time/{id}")
    public ResponseEntity<String> updateAppointmentDateTime(
            @PathVariable(value = "id") long appointment_id,
            @RequestParam("newDate") Date newDate,
            @RequestParam("newTime") Time newTime) throws RecordNotFoundException {
        LOGGER.info("In Controller - Updating appointment date and time for ID: " + appointment_id + " - New date: " + newDate + ", New time: " + newTime);
        service.updateAppointmentDateTime(appointment_id, newDate, newTime);
        return new ResponseEntity<>("Appointment Date and Time Updated Successfully", HttpStatus.OK);
    }

    /**
     * Deletes an appointment with the given appointment ID.
     *
     * @param appointment_id The ID of the appointment to be deleted.
     * @return ResponseEntity<String> A response indicating the success of the delete operation.
     * @throws RecordNotFoundException If no appointment is found with the given ID.
     */
    @DeleteMapping("/delete-appointment/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable(value = "id") long appointment_id) throws RecordNotFoundException {
        LOGGER.info("In Controller - Deleting appointment with ID: " + appointment_id);
        service.deleteAppointment(appointment_id);
        return new ResponseEntity<>("Appointment Deleted Successfully", HttpStatus.OK);
    }

    /**
     * Retrieves a list of appointments scheduled for today.
     *
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/appointments-for-today")
    public ResponseEntity<List<Appointment>> getAppointmentsForToday() {
        LOGGER.info("In Controller - Retrieving appointments for today");
        List<Appointment> appointments = service.getAppointmentsForToday();
        if (appointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for today");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for today: " + appointments);
            return ResponseEntity.ok(appointments);
        }
    }
    
    
    
    @GetMapping("/upcoming-appointments/doctor/{doctorId}/type/{type}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorIdAndStatusAndType(
            @PathVariable long doctorId,
            @PathVariable String type) throws RecordNotFoundException {
        LOGGER.info("In Controller - Retrieving upcoming appointments for doctor ID: " + doctorId + " with status 'Accepted' and type: " + type);
        String status = "Accepted";
        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatusAndType(doctorId, status, type);
        if (upcomingAppointments.isEmpty()) {
            LOGGER.info("In Controller - No upcoming appointments found for doctor ID: " + doctorId + " with status 'Accepted' and type: " + type);
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Upcoming appointments found for doctor ID: " + doctorId + " with status 'Accepted' and type: " + type + ": " + upcomingAppointments);
            return ResponseEntity.ok(upcomingAppointments);
        }
    }
    
    
    

    /**
     * Retrieves a list of upcoming appointments with the status "Accepted".
     *
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsWithStatus() {
        LOGGER.info("In Controller - Retrieving upcoming appointments with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsWithStatus(status);
        LOGGER.info("In Controller - Upcoming appointments with status 'Accepted': " + upcomingAppointments);
        return ResponseEntity.ok(upcomingAppointments);
    }
    
    
    
    @GetMapping("/appointments-for-today/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForTodayByDoctorIdAndStatus(
            @PathVariable long doctorId) throws RecordNotFoundException {
        LOGGER.info("In Controller - Retrieving appointments for today for doctor ID: " + doctorId + " with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> todayAppointments = service.getAppointmentsForTodayByDoctorIdAndStatus(doctorId, status);
        if (todayAppointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for today for doctor ID: " + doctorId + " with status 'Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for today for doctor ID: " + doctorId + " with status 'Accepted': " + todayAppointments);
            return ResponseEntity.ok(todayAppointments);
        }
    }
    
    

    /**
     * Retrieves a list of upcoming appointments for a specific doctor with the status "Accepted".
     *
     * @param doctorId The ID of the doctor for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     * @throws RecordNotFoundException If no appointments are found for the given doctor ID.
     */
    @GetMapping("/upcoming-appointments/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctorIdAndStatus(
            @PathVariable long doctorId) throws RecordNotFoundException {
        LOGGER.info("In Controller - Retrieving upcoming appointments for doctor ID: " + doctorId + " with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
        if (upcomingAppointments.isEmpty()) {
            LOGGER.info("In Controller - No upcoming appointments found for doctor ID: " + doctorId + " with status 'Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Upcoming appointments found for doctor ID: " + doctorId + " with status 'Accepted': " + upcomingAppointments);
            return ResponseEntity.ok(upcomingAppointments);
        }
    }

    
    
    @GetMapping("/appointments-for-today/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForTodayByPatientIdAndStatus(
            @PathVariable long patientId) throws RecordNotFoundException {
        LOGGER.info("In Controller - Retrieving appointments for today for patient ID: " + patientId + " with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> appointments = service.getAppointmentsForTodayByPatientIdAndStatus(patientId, status);
        if (appointments.isEmpty()) {
            LOGGER.info("In Controller - No appointments found for today for patient ID: " + patientId + " with status 'Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Appointments found for today for patient ID: " + patientId + " with status 'Accepted': " + appointments);
            return ResponseEntity.ok(appointments);
        }
    }
    
    
    
    /**
     * Retrieves a list of upcoming appointments for a specific doctor with the status "Accepted".
     *
     * @param patientId The ID of the doctor for whom to retrieve the appointments.
     * @return ResponseEntity<List<Appointment>> A response containing a list of appointments.
     * @throws RecordNotFoundException If no appointments are found for the given doctor ID.
     */
    @GetMapping("/upcoming-appointments/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByPatientIdAndStatus(
            @PathVariable long patientId) throws RecordNotFoundException {
        LOGGER.info("In Controller - Retrieving upcoming appointments for patient ID: " + patientId + " with status 'Accepted'");
        String status = "Accepted";
        List<Appointment> upcomingAppointments = service.getUpcomingAppointmentsByPatientIdAndStatus(patientId, status);
        if (upcomingAppointments.isEmpty()) {
            LOGGER.info("In Controller - No upcoming appointments found for patient ID: " + patientId + " with status 'Accepted'");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("In Controller - Upcoming appointments found for patient ID: " + patientId + " with status 'Accepted': " + upcomingAppointments);
            return ResponseEntity.ok(upcomingAppointments);
        }
    }

    /**
     * Updates the details of an appointment with the given appointment ID.
     *
     * @param appointmentId The ID of the appointment to be updated.
     * @param updatedAppointment The updated Appointment object.
     * @return ResponseEntity<String> A response indicating the success of the update operation.
     * @throws RecordNotFoundException If no appointment is found with the given ID.
     */
    @PutMapping("/update-appointment/{id}")
    public ResponseEntity<String> updateAppointment(
            @PathVariable(value = "id") long appointmentId,
            @RequestBody Appointment updatedAppointment) throws RecordNotFoundException {
        LOGGER.info("In Controller - Updating appointment with ID: " + appointmentId + " to: " + updatedAppointment);
        service.updateAppointment(appointmentId, updatedAppointment);
        return new ResponseEntity<>("Appointment Updated Successfully", HttpStatus.OK);
    }
    
    
    
    
    
    @GetMapping("/count/{doctorId}")
    public ResponseEntity<Long> getTodayAppointmentsCountByDoctorAndStatus(
            @PathVariable long doctorId
    ) 
    {
        String status ="Accepted";
    	long count = service.getTodayAppointmentsCountByDoctorIdAndStatus(doctorId, status);
        return ResponseEntity.ok(count);
    }
    
    
    
    @GetMapping("/count/upcoming/{doctorId}")
    public ResponseEntity<Long> getCountOfUpcomingAppointmentsByDoctorIdAndStatus(
            @PathVariable Long doctorId)
    {
    	String status = "Accepted";
        Long count = service.getCountOfUpcomingAppointmentsByDoctorIdAndStatus(doctorId, status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    
}
