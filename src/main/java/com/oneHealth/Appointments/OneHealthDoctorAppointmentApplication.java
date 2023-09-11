package com.oneHealth.Appointments;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * This is the main class for the OneHealthDoctorAppointmentApplication.
 * It is annotated with @SpringBootApplication, indicating that it's a Spring Boot application.
 * It's also annotated with @RestController, indicating that it's a RESTful controller.
 * 
 * @author Anup
 * @version 1.0
 */
@SpringBootApplication
@RestController
public class OneHealthDoctorAppointmentApplication {

    public static void main(String[] args) {
        // This method starts the Spring Boot application.
        SpringApplication.run(OneHealthDoctorAppointmentApplication.class, args);
    }

    @GetMapping
    public String Welcome() {
        // This method handles GET requests to the root URL and returns a welcome message.
        return "Welcome From OneHealth Team (OneHealth-DoctorAppointmentService)!!!";
    }

    @Bean
    public ModelMapper modelMapper() {
        // This method creates and configures a ModelMapper bean.
        return new ModelMapper();
    }

   
}
