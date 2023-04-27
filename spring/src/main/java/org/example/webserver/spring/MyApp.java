package org.example.webserver.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Spring Boot application.
 * <p>
 * This class uses the {@link SpringBootApplication} annotation to enable auto-configuration and component scanning.
 * The {@link SpringApplication} is used to start the Spring Boot application.
 */
@SpringBootApplication
public class MyApp {
    
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
