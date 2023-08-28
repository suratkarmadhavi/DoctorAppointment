package com.oneHealth.Appointments.serviceImplementation;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.oneHealth.Appointments.DTO.AppointmentDTO;
import com.oneHealth.Appointments.DTO.DoctorProfile;
import com.oneHealth.Appointments.DTO.Patient;
import com.oneHealth.Appointments.entity.Appointment;
import com.oneHealth.Appointments.exception.ProfileNotFoundException;
import com.oneHealth.Appointments.exception.RecordNotFoundException;
import com.oneHealth.Appointments.repository.AppointmentRepository;
import com.oneHealth.Appointments.service.AppointmentService;

/**
 * Service implementation class that provides the business logic for handling
 * appointment-related operations.
 * 
 * @author Anup
 * @version 3.9.10
 */
@Service
public class AppointmentServiceImplementation implements AppointmentService {
	private static final Logger LOGGER = Logger.getLogger(AppointmentServiceImplementation.class.getName());

	@Autowired
	private AppointmentRepository repo;

	@Autowired
	private WebClient.Builder builder;

	@Autowired
	private ModelMapper mapper;



	/**
	 * Saves the details of a new appointment into the database.
	 *
	 * @param obj The Appointment object to be saved.
	 * @return The saved Appointment object.
	 */
	@Override
	public Appointment saveAppointment(Appointment obj) {
		LOGGER.info("In Service - Saving appointment: " + obj);

		// Fetch patient details using WebClient
        Patient patientDto = builder.build()
            .get()
            .uri("https://apigateway-yjb28-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/patientProfile/{patient_id}", obj.getPatientId())
            .retrieve()
            .bodyToMono(Patient.class)
            .block();

//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity entity = new HttpEntity(headers);
//		String patientDto = restTemplate.exchange(
//				"https://apigateway-yjb28-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/patientProfile/10",
//				HttpMethod.GET, entity, String.class).getBody();


		System.out.println(patientDto);

		// Fetch doctor profile using WebClient
		DoctorProfile profile = builder.build().get().uri(
				"https://apigateway-yjb28-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/api/doctors/addressprofileregistration/getdoctorprofile/{doctor_id}",
				obj.getDoctorId()).retrieve().bodyToMono(DoctorProfile.class).block();
		System.out.println(profile);

		// Create an AppointmentDTO and map relevant fields
		AppointmentDTO dto = new AppointmentDTO();
		mapper.map(obj, dto);
		dto.setDoctor_name(profile.getFirst_name() + " " + profile.getLast_name());
		dto.setContact(profile.getContact());

		// Set patient and doctor email addresses (you might want to dynamically fetch
		// patient email)
		dto.setPatient_email(patientDto.getEmailId());
		dto.setDoctor_email(profile.getEmail());

		// Prepare and send appointment email using WebClient
		WebClient.ResponseSpec responseSpec = builder.baseUrl("https://apigateway-yjb28-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/emailService").build().post()
				.uri("/appointmentEmail").body(BodyInserters.fromValue(dto)).retrieve(); // This prepares the request

		// Send the request and handle the response
		responseSpec.toBodilessEntity().subscribe();

		// Save the appointment details to the repository
		return repo.save(obj);
	}

	/**
	 * Retrieves a list of appointments for a specific patient ID.
	 *
	 * @param patientId The ID of the patient.
	 * @return List of appointments associated with the patient ID.
	 */
	@Override
	public List<Appointment> findByPatientId(long patientId) {
		LOGGER.info("In Service - Finding appointments by patient ID: " + patientId);
		return repo.findByPatientId(patientId);
	}

	/**
	 * Retrieves a list of appointments for a specific doctor ID.
	 *
	 * @param doctorId The ID of the doctor.
	 * @return List of appointments associated with the doctor ID.
	 */
	@Override
	public List<Appointment> findByDoctorId(long doctorId) {
		LOGGER.info("Finding appointments by doctor ID: " + doctorId);
		return repo.findByDoctorId(doctorId);
	}

	/**
	 * Retrieves a list of appointments for a specific doctor ID and status.
	 *
	 * @param doctorId The ID of the doctor.
	 * @param status   The status of the appointments (e.g., "Accepted", "Pending",
	 *                 etc.).
	 * @return List of appointments matching the specified doctor ID and status.
	 */
	@Override
	public List<Appointment> findByDoctorIdAndStatus(long doctorId, String status) {
		LOGGER.info("Finding appointments by doctor ID: " + doctorId + " and status: " + status);
		return repo.findByDoctorIdAndStatus(doctorId, status);
	}

	/**
	 * Retrieves a list of appointments for a specific patient ID and type.
	 *
	 * @param patientId The ID of the patient.
	 * @param type      The type of appointments (e.g., "Checkup", "Follow-up",
	 *                  etc.).
	 * @return List of appointments matching the specified patient ID and type.
	 */
	@Override
	public List<Appointment> findByPatientIdAndType(long patientId, String type) {
		LOGGER.info("In Service - Finding appointments by patient ID: " + patientId + " and type: " + type);
		return repo.findByPatientIdAndType(patientId, type);
	}

	/**
	 * Updates the status of an appointment by appointment ID.
	 *
	 * @param appointment_id The ID of the appointment.
	 * @param newStatus      The new status to update.
	 * @throws ProfileNotFoundException If no appointment is found with the given
	 *                                  ID.
	 */
	@Override
	public void updateAppointmentStatus(long appointment_id, String newStatus) throws ProfileNotFoundException {
		LOGGER.info("In Service - Updating appointment status for ID: " + appointment_id + " to: " + newStatus);
		Appointment appointment = repo.findById(appointment_id)
				.orElseThrow(() -> new ProfileNotFoundException("Appointment not found with ID: " + appointment_id));

		appointment.setStatus(newStatus);
		repo.save(appointment);
	}

	/**
	 * Updates the date and time of an appointment by appointment ID.
	 *
	 * @param appointment_id The ID of the appointment.
	 * @param newDate        The new date to update.
	 * @param newTime        The new time to update.
	 * @return The updated Appointment object.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@Override
	public Appointment updateAppointmentDateTime(long appointment_id, Date newDate, Time newTime)
			throws RecordNotFoundException {
		LOGGER.info("Updating appointment date and time for ID: " + appointment_id + " - New date: " + newDate
				+ ", New time: " + newTime);
		Appointment appointment = repo.findById(appointment_id)
				.orElseThrow(() -> new RecordNotFoundException("No Appointment Found with ID: " + appointment_id));

		appointment.setDate(newDate);
		appointment.setAppointmentTime(newTime);

		return repo.save(appointment);
	}

	/**
	 * Deletes an appointment by appointment ID.
	 *
	 * @param appointment_id The ID of the appointment to be deleted.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@Override
	public void deleteAppointment(long appointment_id) throws RecordNotFoundException {
		LOGGER.info("Deleting appointment with ID: " + appointment_id);
		Appointment appointment = repo.findById(appointment_id)
				.orElseThrow(() -> new RecordNotFoundException("No Appointment Found with ID: " + appointment_id));

		repo.delete(appointment);
	}

	/**
	 * Retrieves a list of appointments for the current day.
	 *
	 * @return List of appointments for the current day.
	 */
	@Override
	public List<Appointment> getAppointmentsForToday() {
		LOGGER.info("Retrieving appointments for today");
		LocalDate currentDate = LocalDate.now();
		return repo.findByDate(currentDate);
	}

	/**
	 * Retrieves a list of upcoming appointments with a specific status.
	 *
	 * @param status The status of the appointments (e.g., "Accepted", "Pending",
	 *               etc.).
	 * @return List of upcoming appointments with the specified status.
	 */
	@Override
	public List<Appointment> getUpcomingAppointmentsWithStatus(String status) {
		LOGGER.info("Retrieving upcoming appointments with status: " + status);
		LocalDate currentDate = LocalDate.now();
		Date sqlDate = Date.valueOf(currentDate);
		return repo.findByDateAfterAndStatus(sqlDate, status);
	}

	/**
	 * Retrieves a list of upcoming appointments for a specific doctor ID and
	 * status.
	 *
	 * @param doctorId The ID of the doctor.
	 * @param status   The status of the appointments (e.g., "Accepted", "Pending",
	 *                 etc.).
	 * @return List of upcoming appointments for the specified doctor ID and status.
	 * @throws RecordNotFoundException If no upcoming appointments are found.
	 */
	@Override
	public List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatus(long doctorId, String status)
			throws RecordNotFoundException {
		LOGGER.info("Retrieving upcoming appointments for Doctor ID: " + doctorId + " with status: " + status);
		LocalDate todayDate = LocalDate.now();
		List<Appointment> upcomingAppointments = repo.findByDateAfterAndDoctorIdAndStatus(todayDate, doctorId, status);

		if (upcomingAppointments.isEmpty()) {
			LOGGER.warning("No upcoming appointments found for Doctor ID: " + doctorId + " and status: " + status);
			throw new RecordNotFoundException(
					"No upcoming appointments found for Doctor ID: " + doctorId + " and status: " + status);
		}

		return upcomingAppointments;
	}

	/**
	 * Retrieves a list of upcoming appointments for a specific doctor ID, status,
	 * and type.
	 *
	 * @param doctorId The ID of the doctor.
	 * @param status   The status of the appointments (e.g., "Accepted", "Pending",
	 *                 etc.).
	 * @param type     The type of appointments (e.g., "Checkup", "Follow-up",
	 *                 etc.).
	 * @return List of upcoming appointments for the specified doctor ID, status,
	 *         and type.
	 * @throws RecordNotFoundException If no upcoming appointments are found.
	 */
	@Override
	public List<Appointment> getUpcomingAppointmentsByDoctorIdAndStatusAndType(long doctorId, String status,
			String type) throws RecordNotFoundException {
		LOGGER.info("In Service - Retrieving upcoming appointments for Doctor ID: " + doctorId + " with status: "
				+ status + " and type: " + type);
		Date todayDate = Date.valueOf(LocalDate.now());
		List<Appointment> upcomingAppointments = repo.findByDateAfterAndDoctorIdAndStatusAndType(todayDate, doctorId,
				status, type);

		if (upcomingAppointments.isEmpty()) {
			LOGGER.warning("In Service - No upcoming appointments found for Doctor ID: " + doctorId + " with status: "
					+ status + " and type: " + type);
			throw new RecordNotFoundException("No upcoming appointments found for Doctor ID: " + doctorId
					+ " with status: " + status + " and type: " + type);
		}

		return upcomingAppointments;
	}

	/**
	 * Retrieves a list of appointments for the current day by doctor ID and status.
	 *
	 * @param doctorId The ID of the doctor.
	 * @param status   The status of the appointments (e.g., "Accepted", "Pending",
	 *                 etc.).
	 * @return List of appointments for the current day by doctor ID and status.
	 * @throws RecordNotFoundException If no appointments are found.
	 */
	@Override
	public List<Appointment> getAppointmentsForTodayByDoctorIdAndStatus(long doctorId, String status)
			throws RecordNotFoundException {
		LOGGER.info("In Service - Retrieving appointments for today for Doctor ID: " + doctorId + " with status: "
				+ status);
		LocalDate currentDate = LocalDate.now();
		List<Appointment> todayAppointments = repo.findByDateAndDoctorIdAndStatus(currentDate, doctorId, status);

		if (todayAppointments.isEmpty()) {
			LOGGER.warning("In Service - No today's appointments found for Doctor ID: " + doctorId + " with status: "
					+ status);
			throw new RecordNotFoundException(
					"No today's appointments found for Doctor ID: " + doctorId + " with status: " + status);
		}

		return todayAppointments;
	}

	/**
	 * Retrieves a list of appointments for the current day by patient ID and
	 * status.
	 *
	 * @param patientId The ID of the patient.
	 * @param status    The status of the appointments (e.g., "Accepted", "Pending",
	 *                  etc.).
	 * @return List of appointments for the current day by patient ID and status.
	 */
	@Override
	public List<Appointment> getAppointmentsForTodayByPatientIdAndStatus(long patientId, String status) {
		LOGGER.info("In Service - Retrieving appointments for today for Patient ID: " + patientId + " with status: "
				+ status);
		LocalDate currentDate = LocalDate.now();
		return repo.findByDateAndPatientIdAndStatus(currentDate, patientId, status);
	}

	/**
	 * Retrieves a list of upcoming appointments for a specific patient ID and
	 * status.
	 *
	 * @param patientId The ID of the patient.
	 * @param status    The status of the appointments (e.g., "Accepted", "Pending",
	 *                  etc.).
	 * @return List of upcoming appointments for the specified patient ID and
	 *         status.
	 * @throws RecordNotFoundException If no upcoming appointments are found.
	 */
	@Override
	public List<Appointment> getUpcomingAppointmentsByPatientIdAndStatus(long patientId, String status)
			throws RecordNotFoundException {
		LOGGER.info("In Service - Retrieving upcoming appointments for Patient ID: " + patientId + " with status: "
				+ status);
		LocalDate currentDate = LocalDate.now();
		Date sqlDate = Date.valueOf(currentDate);
		return repo.findByDateAfterAndPatientIdAndStatus(sqlDate, patientId, status);
	}

	/**
	 * Updates the details of an appointment.
	 *
	 * @param appointmentId      The ID of the appointment to be updated.
	 * @param updatedAppointment The updated Appointment object.
	 * @return The updated Appointment object.
	 * @throws RecordNotFoundException If no appointment is found with the given ID.
	 */
	@Override
	public Appointment updateAppointment(long appointmentId, Appointment updatedAppointment)
			throws RecordNotFoundException {
		LOGGER.info("In Service - Updating appointment with ID: " + appointmentId + " to: " + updatedAppointment);
		Appointment existingAppointment = repo.findById(appointmentId)
				.orElseThrow(() -> new RecordNotFoundException("No Appointment Found with ID: " + appointmentId));

		// Update all fields based on the updatedAppointment object
		existingAppointment.setPatient_name(updatedAppointment.getPatient_name());
		existingAppointment.setDoctorName(updatedAppointment.getDoctorName());
		existingAppointment.setAge(updatedAppointment.getAge());
		existingAppointment.setGender(updatedAppointment.getGender());
		existingAppointment.setDescription(updatedAppointment.getDescription());
		existingAppointment.setDate(updatedAppointment.getDate());
		existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
		existingAppointment.setType(updatedAppointment.getType());
		existingAppointment.setPayment_mode(updatedAppointment.getPayment_mode());
		existingAppointment.setTransaction_id(updatedAppointment.getTransaction_id());
		existingAppointment.setAddress(updatedAppointment.getAddress());

		return repo.save(existingAppointment);
	}

	@Override
	public long getTodayAppointmentsCountByDoctorIdAndStatus(long doctorId, String status) {
		LocalDate today = LocalDate.now();
		return repo.countByDoctorIdAndStatusAndDate(doctorId, status, today);
	}

	@Override
	public Long getCountOfUpcomingAppointmentsByDoctorIdAndStatus(Long doctorId, String status) {
		LocalDate todayDate = LocalDate.now();
		List<Appointment> upcomingAppointments = repo.findByDateAfterAndDoctorIdAndStatus(todayDate, doctorId, status);

		return (long) upcomingAppointments.size();
	}

	@Override
	public List<Appointment> findByPatientIdAndStatus(long patientId, String status) {
		return repo.findByPatientIdAndStatus(patientId, status);
	}

	@Override
	public List<Appointment> NotAcceptedAppointmentsForRequest(long doctorId, String status) {
		LocalDate todayDate = LocalDate.now();
		List<Appointment> today = repo.findByDateAndDoctorIdAndStatus(todayDate, doctorId, status);
		List<Appointment> upcoming = repo.findByDateAfterAndDoctorIdAndStatus(todayDate, doctorId, status);
		List<Appointment> Both = new ArrayList<>();
		Both.addAll(upcoming);
		Both.addAll(today);
		return Both;
	}

	public static AppointmentDTO createDummyAppointment() {
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setAppointment_id(1);
		appointment.setDoctorId(123);
		appointment.setPatientId(456);
		appointment.setPatient_name("John Doe");
		appointment.setAge(30);
		appointment.setGender("Male");
		appointment.setDescription("Regular checkup");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			appointment.setDate(new Date(dateFormat.parse("2023-08-22").getTime()));
			appointment.setAppointmentTime(new Time(timeFormat.parse("15:30:00").getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		appointment.setStatus("Scheduled");
		appointment.setType("General");
		appointment.setPayment_mode("Credit Card");
		appointment.setTransaction_id("ABC123XYZ");
		appointment.setAddress("123 Main St, City");
		appointment.setAmount_paid(100);
		appointment.setPatient_email("varungarade1151@gmail.com");

		return appointment;
	}

	@Override

    public List<Appointment> findUpcomingByPatientId(long patientId) {

        LocalDate currentDate = LocalDate.now();

        List<Appointment>upcoming = repo.findByDateAfterAndPatientId(currentDate, patientId);

        List<Appointment>today = repo.findByDateAndPatientId(currentDate, patientId);

        List<Appointment>Both = new ArrayList<>();

        Both.addAll(upcoming);

        Both.addAll(today);

        return Both;

    }

}
