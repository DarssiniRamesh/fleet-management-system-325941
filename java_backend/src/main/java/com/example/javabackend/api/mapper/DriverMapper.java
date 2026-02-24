package com.example.javabackend.api.mapper;

import com.example.javabackend.api.dto.driver.DriverResponse;
import com.example.javabackend.domain.Driver;

/**
 * Driver mapping utilities (entity <-> DTO).
 */
public final class DriverMapper {

    private DriverMapper() {}

    // PUBLIC_INTERFACE
    public static DriverResponse toResponse(Driver driver) {
        /** Convert a Driver entity to API response DTO. */
        if (driver == null) {
            return null;
        }
        return new DriverResponse(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getLicenseNumber(),
                driver.getStatus());
    }
}
