package com.example.javabackend.service;

import com.example.javabackend.api.dto.driver.DriverCreateRequest;
import com.example.javabackend.api.dto.driver.DriverUpdateRequest;
import com.example.javabackend.api.error.ResourceConflictException;
import com.example.javabackend.api.error.ResourceNotFoundException;
import com.example.javabackend.domain.Driver;
import com.example.javabackend.repository.DriverRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Driver CRUD flow (service/orchestration layer).
 *
 * <p>Contract:
 * <ul>
 *   <li>Throws {@link ResourceNotFoundException} when id does not exist</li>
 *   <li>Throws {@link ResourceConflictException} when uniqueness constraints would be violated</li>
 * </ul>
 */
@Service
public class DriverService {

    private static final Logger log = LoggerFactory.getLogger(DriverService.class);

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public List<Driver> listDrivers() {
        /** List all drivers. */
        return driverRepository.findAll();
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public Driver getDriver(long id) {
        /** Get a driver by id. */
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id=" + id));
    }

    // PUBLIC_INTERFACE
    @Transactional
    public Driver createDriver(DriverCreateRequest request) {
        /** Create a driver from a validated request payload. */
        log.info("DriverCreateFlow: start email={} licenseNumber={}", safe(request.getEmail()), safe(request.getLicenseNumber()));

        ensureDriverUniquenessOnCreate(request.getEmail(), request.getLicenseNumber());

        Driver d = new Driver();
        d.setFirstName(request.getFirstName().trim());
        d.setLastName(request.getLastName().trim());
        d.setEmail(trimToNull(request.getEmail()));
        d.setPhone(trimToNull(request.getPhone()));
        d.setLicenseNumber(trimToNull(request.getLicenseNumber()));
        d.setStatus(trimToNull(request.getStatus()));

        Driver saved = driverRepository.save(d);
        log.info("DriverCreateFlow: success id={}", saved.getId());
        return saved;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public Driver updateDriver(long id, DriverUpdateRequest request) {
        /** Update an existing driver by id. */
        log.info("DriverUpdateFlow: start id={}", id);

        Driver existing = getDriver(id);

        String newEmail = request.getEmail() != null ? trimToNull(request.getEmail()) : null;
        String newLicense = request.getLicenseNumber() != null ? trimToNull(request.getLicenseNumber()) : null;

        ensureDriverUniquenessOnUpdate(existing, newEmail, newLicense);

        if (request.getFirstName() != null) {
            existing.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null) {
            existing.setLastName(request.getLastName().trim());
        }
        if (request.getEmail() != null) {
            existing.setEmail(newEmail);
        }
        if (request.getPhone() != null) {
            existing.setPhone(trimToNull(request.getPhone()));
        }
        if (request.getLicenseNumber() != null) {
            existing.setLicenseNumber(newLicense);
        }
        if (request.getStatus() != null) {
            existing.setStatus(trimToNull(request.getStatus()));
        }

        Driver saved = driverRepository.save(existing);
        log.info("DriverUpdateFlow: success id={}", saved.getId());
        return saved;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void deleteDriver(long id) {
        /** Delete a driver by id. No-op is NOT allowed; not-found throws. */
        log.info("DriverDeleteFlow: start id={}", id);
        Driver existing = getDriver(id);
        driverRepository.delete(existing);
        log.info("DriverDeleteFlow: success id={}", id);
    }

    private void ensureDriverUniquenessOnCreate(String email, String licenseNumber) {
        String emailNorm = trimToNull(email);
        String licenseNorm = trimToNull(licenseNumber);

        if (emailNorm != null && driverRepository.existsByEmail(emailNorm)) {
            throw new ResourceConflictException("Driver", "email", "email already exists");
        }
        if (licenseNorm != null && driverRepository.existsByLicenseNumber(licenseNorm)) {
            throw new ResourceConflictException("Driver", "licenseNumber", "licenseNumber already exists");
        }
    }

    private void ensureDriverUniquenessOnUpdate(Driver existing, String newEmail, String newLicense) {
        if (newEmail != null) {
            String current = existing.getEmail();
            if (current == null || !current.equalsIgnoreCase(newEmail)) {
                if (driverRepository.existsByEmail(newEmail)) {
                    throw new ResourceConflictException("Driver", "email", "email already exists");
                }
            }
        }
        if (newLicense != null) {
            String current = existing.getLicenseNumber();
            if (current == null || !current.equals(newLicense)) {
                if (driverRepository.existsByLicenseNumber(newLicense)) {
                    throw new ResourceConflictException("Driver", "licenseNumber", "licenseNumber already exists");
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
