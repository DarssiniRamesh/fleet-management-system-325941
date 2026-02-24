package com.example.javabackend.api.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VehicleResponse", description = "Vehicle representation returned by the API")
public class VehicleResponse {

    @Schema(description = "Vehicle id", example = "1")
    private Long id;

    @Schema(description = "Vehicle identification number (VIN)", example = "1HGCM82633A004352")
    private String vin;

    @Schema(description = "License plate", example = "ABC-1234")
    private String licensePlate;

    @Schema(description = "Make", example = "Toyota")
    private String make;

    @Schema(description = "Model", example = "Camry")
    private String model;

    @Schema(description = "Model year", example = "2022")
    private Integer year;

    @Schema(description = "Status", example = "ACTIVE")
    private String status;

    public VehicleResponse() {}

    public VehicleResponse(Long id, String vin, String licensePlate, String make, String model, Integer year, String status) {
        this.id = id;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
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
