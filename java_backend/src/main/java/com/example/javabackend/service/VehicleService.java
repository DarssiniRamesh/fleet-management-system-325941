package com.example.javabackend.service;

import com.example.javabackend.api.dto.vehicle.VehicleCreateRequest;
import com.example.javabackend.api.dto.vehicle.VehicleUpdateRequest;
import com.example.javabackend.api.error.ResourceConflictException;
import com.example.javabackend.api.error.ResourceNotFoundException;
import com.example.javabackend.domain.Vehicle;
import com.example.javabackend.repository.VehicleRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Vehicle CRUD flow (service/orchestration layer).
 *
 * <p>Contract:
 * <ul>
 *   <li>Throws {@link ResourceNotFoundException} when id does not exist</li>
 *   <li>Throws {@link ResourceConflictException} when uniqueness constraints would be violated</li>
 * </ul>
 */
@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public List<Vehicle> listVehicles() {
        /** List all vehicles. */
        return vehicleRepository.findAll();
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public Vehicle getVehicle(long id) {
        /** Get a vehicle by id. */
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id=" + id));
    }

    // PUBLIC_INTERFACE
    @Transactional
    public Vehicle createVehicle(VehicleCreateRequest request) {
        /** Create a vehicle from a validated request payload. */
        log.info("VehicleCreateFlow: start vin={} licensePlate={}", safe(request.getVin()), safe(request.getLicensePlate()));

        ensureVehicleUniquenessOnCreate(request.getVin(), request.getLicensePlate());

        Vehicle v = new Vehicle();
        v.setVin(trimToNull(request.getVin()));
        v.setLicensePlate(trimToNull(request.getLicensePlate()));
        v.setMake(trimToNull(request.getMake()));
        v.setModel(trimToNull(request.getModel()));
        v.setYear(request.getYear());
        v.setStatus(trimToNull(request.getStatus()));

        Vehicle saved = vehicleRepository.save(v);

        log.info("VehicleCreateFlow: success id={}", saved.getId());
        return saved;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public Vehicle updateVehicle(long id, VehicleUpdateRequest request) {
        /** Update an existing vehicle by id. */
        log.info("VehicleUpdateFlow: start id={}", id);

        Vehicle existing = getVehicle(id);

        // Validate uniqueness for updated fields when present.
        String newVin = request.getVin() != null ? trimToNull(request.getVin()) : null;
        String newLicensePlate = request.getLicensePlate() != null ? trimToNull(request.getLicensePlate()) : null;

        ensureVehicleUniquenessOnUpdate(existing, newVin, newLicensePlate);

        if (request.getVin() != null) {
            existing.setVin(newVin);
        }
        if (request.getLicensePlate() != null) {
            existing.setLicensePlate(newLicensePlate);
        }
        if (request.getMake() != null) {
            existing.setMake(trimToNull(request.getMake()));
        }
        if (request.getModel() != null) {
            existing.setModel(trimToNull(request.getModel()));
        }
        if (request.getYear() != null) {
            existing.setYear(request.getYear());
        }
        if (request.getStatus() != null) {
            existing.setStatus(trimToNull(request.getStatus()));
        }

        Vehicle saved = vehicleRepository.save(existing);
        log.info("VehicleUpdateFlow: success id={}", saved.getId());
        return saved;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void deleteVehicle(long id) {
        /** Delete a vehicle by id. No-op is NOT allowed; not-found throws. */
        log.info("VehicleDeleteFlow: start id={}", id);
        Vehicle existing = getVehicle(id);
        vehicleRepository.delete(existing);
        log.info("VehicleDeleteFlow: success id={}", id);
    }

    private void ensureVehicleUniquenessOnCreate(String vin, String licensePlate) {
        String vinNorm = trimToNull(vin);
        String plateNorm = trimToNull(licensePlate);

        if (vinNorm != null && vehicleRepository.existsByVin(vinNorm)) {
            throw new ResourceConflictException("Vehicle", "vin", "vin already exists");
        }
        if (plateNorm != null && vehicleRepository.existsByLicensePlate(plateNorm)) {
            throw new ResourceConflictException("Vehicle", "licensePlate", "licensePlate already exists");
        }
    }

    private void ensureVehicleUniquenessOnUpdate(Vehicle existing, String newVin, String newLicensePlate) {
        if (newVin != null) {
            String current = existing.getVin();
            if (current == null || !current.equals(newVin)) {
                if (vehicleRepository.existsByVin(newVin)) {
                    throw new ResourceConflictException("Vehicle", "vin", "vin already exists");
                }
            }
        }
        if (newLicensePlate != null) {
            String current = existing.getLicensePlate();
            if (current == null || !current.equals(newLicensePlate)) {
                if (vehicleRepository.existsByLicensePlate(newLicensePlate)) {
                    throw new ResourceConflictException("Vehicle", "licensePlate", "licensePlate already exists");
                }
            }
        }
    }

    private static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String safe(String s) {
        return s == null ? null : s.trim();
    }
}
