package com.example.data.db.repository;

import com.example.data.db.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByStatus(String status);
    Optional<Car> findCarByStatusAndVin(String status, String vin);
    Optional<Car> findCarByVin(String vin);
}
