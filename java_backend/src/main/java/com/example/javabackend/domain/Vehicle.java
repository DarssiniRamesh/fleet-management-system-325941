package com.example.javabackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Vehicle domain entity.
 *
 * <p>Invariants:
 * <ul>
 *   <li>vin is globally unique when present</li>
 *   <li>licensePlate is unique when present</li>
 * </ul>
 */
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Vehicle Identification Number (VIN).
     * Optional, but if provided should be unique.
     */
    @Column(name = "vin", unique = true, length = 64)
    private String vin;

    @Column(name = "license_plate", unique = true, length = 32)
    private String licensePlate;

    @Column(name = "make", length = 64)
    private String make;

    @Column(name = "model", length = 64)
    private String model;

    @Column(name = "model_year")
    private Integer year;

    @Column(name = "status", length = 32)
    private String status;

    public Vehicle() {
        // JPA
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
