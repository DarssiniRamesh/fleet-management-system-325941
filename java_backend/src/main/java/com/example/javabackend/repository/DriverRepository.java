package com.example.javabackend.repository;

import com.example.javabackend.domain.Driver;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByEmail(String email);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);
}
