package com.example.javabackend.api.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(name = "VehicleCreateRequest", description = "Payload to create a vehicle")
public class VehicleCreateRequest {

    @Schema(description = "Vehicle identification number (unique if provided)", example = "1HGCM82633A004352")
    @Size(max = 64, message = "vin must be at most 64 characters")
    private String vin;

    @Schema(description = "License plate (unique if provided)", example = "ABC-1234")
    @Size(max = 32, message = "licensePlate must be at most 32 characters")
    private String licensePlate;

    @Schema(description = "Make", example = "Toyota")
    @Size(max = 64, message = "make must be at most 64 characters")
    private String make;

    @Schema(description = "Model", example = "Camry")
    @Size(max = 64, message = "model must be at most 64 characters")
    private String model;

    @Schema(description = "Model year", example = "2022", minimum = "1886", maximum = "2100")
    @Min(value = 1886, message = "year must be >= 1886")
    @Max(value = 2100, message = "year must be <= 2100")
    private Integer year;

    @Schema(description = "Status (e.g., ACTIVE, INACTIVE, MAINTENANCE)", example = "ACTIVE")
    @Size(max = 32, message = "status must be at most 32 characters")
    private String status;

    public String getVin() {
        return vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
