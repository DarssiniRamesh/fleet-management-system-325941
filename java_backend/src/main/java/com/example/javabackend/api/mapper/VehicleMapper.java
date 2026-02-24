package com.example.javabackend.api.mapper;

import com.example.javabackend.api.dto.vehicle.VehicleResponse;
import com.example.javabackend.domain.Vehicle;

/**
 * Vehicle mapping utilities (entity <-> DTO).
 */
public final class VehicleMapper {

    private VehicleMapper() {}

    // PUBLIC_INTERFACE
    public static VehicleResponse toResponse(Vehicle vehicle) {
        /** Convert a Vehicle entity to API response DTO. */
        if (vehicle == null) {
            return null;
        }
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getVin(),
                vehicle.getLicensePlate(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getStatus());
    }
}
