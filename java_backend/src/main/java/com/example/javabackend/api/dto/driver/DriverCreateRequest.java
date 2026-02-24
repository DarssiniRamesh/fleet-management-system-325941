package com.example.javabackend.api.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "DriverCreateRequest", description = "Payload to create a driver")
public class DriverCreateRequest {

    @Schema(description = "First name", example = "Alex")
    @NotBlank(message = "firstName is required")
    @Size(max = 64, message = "firstName must be at most 64 characters")
    private String firstName;

    @Schema(description = "Last name", example = "Johnson")
    @NotBlank(message = "lastName is required")
    @Size(max = 64, message = "lastName must be at most 64 characters")
    private String lastName;

    @Schema(description = "Email address (unique if provided)", example = "alex.johnson@example.com")
    @Email(message = "email must be a valid email address")
    @Size(max = 254, message = "email must be at most 254 characters")
    private String email;

    @Schema(description = "Phone number", example = "+1-555-0100")
    @Size(max = 32, message = "phone must be at most 32 characters")
    private String phone;

    @Schema(description = "Driver license number (unique if provided)", example = "D1234567")
    @Size(max = 64, message = "licenseNumber must be at most 64 characters")
    private String licenseNumber;

    @Schema(description = "Status (e.g., ACTIVE, INACTIVE, SUSPENDED)", example = "ACTIVE")
    @Size(max = 32, message = "status must be at most 32 characters")
    private String status;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
