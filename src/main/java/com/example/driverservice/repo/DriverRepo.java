package com.example.driverservice.repo;

import com.example.driverservice.model.Driver;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {
    @Timed
    List<Driver> findAllByAvailability(boolean availability);
    @Timed
    Optional<Driver> findByEmailOrUsername(String email, String username);
}
