package com.example.javabackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info =
                @Info(
                        title = "Fleet Management Backend API",
                        version = "0.1.0",
                        description = "Spring Boot backend for fleet management: vehicles, drivers, maintenance, and reporting."),
        tags = {
            @Tag(name = "Vehicles", description = "Vehicle management endpoints"),
            @Tag(name = "Drivers", description = "Driver management endpoints"),
            @Tag(name = "Hello Controller", description = "Basic endpoints for javabackend")
        })
public class JavabackendApplication {

    // PUBLIC_INTERFACE
    public static void main(String[] args) {
        /** Spring Boot application entrypoint. */
        SpringApplication.run(JavabackendApplication.class, args);
    }
}
