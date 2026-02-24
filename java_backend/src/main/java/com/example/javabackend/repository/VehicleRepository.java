package com.example.javabackend.repository;

import com.example.javabackend.domain.Vehicle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByVin(String vin);

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    boolean existsByVin(String vin);

    boolean existsByLicensePlate(String licensePlate);
}
