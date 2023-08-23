package com.oneHealth.Appointments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppointmentConfig {

    // This class is a Spring configuration class responsible for configuring beans.

    @Bean
    // Indicates that the following method will define a bean to be managed by Spring.
    public WebClient.Builder builder(){
        // Create and return an instance of WebClient.Builder.
        return WebClient.builder();
    }
    
    // This method defines a bean of type WebClient.Builder.
    // The bean can be used to create instances of WebClient for making web requests.
}
