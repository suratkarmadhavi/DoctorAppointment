package com.oneHealth.Appointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class OneHealthDoctorAppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneHealthDoctorAppointmentApplication.class, args);
	}
	@GetMapping
	public String Welcome() {
		
		return "Welcome From OneHealth Team (OneHealth-DoctorAppointmentService)!!!";
	}

}
