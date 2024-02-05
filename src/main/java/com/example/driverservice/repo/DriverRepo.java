package com.example.driverservice.repo;

import com.example.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {

    Optional<Driver> findByEmailAndPassword(String email, String password);
    List<Driver> findAllByAvailability(boolean availability);
    Optional<Driver> findByEmailOrUsername(String email, String username);
}
