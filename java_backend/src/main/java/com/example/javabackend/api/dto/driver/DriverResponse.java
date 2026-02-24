package com.example.javabackend.api.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DriverResponse", description = "Driver representation returned by the API")
public class DriverResponse {

    @Schema(description = "Driver id", example = "1")
    private Long id;

    @Schema(description = "First name", example = "Alex")
    private String firstName;

    @Schema(description = "Last name", example = "Johnson")
    private String lastName;

    @Schema(description = "Email address", example = "alex.johnson@example.com")
    private String email;

    @Schema(description = "Phone number", example = "+1-555-0100")
    private String phone;

    @Schema(description = "Driver license number", example = "D1234567")
    private String licenseNumber;

    @Schema(description = "Status", example = "ACTIVE")
    private String status;

    public DriverResponse() {}

    public DriverResponse(
            Long id, String firstName, String lastName, String email, String phone, String licenseNumber, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
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
