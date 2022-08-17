package com.example.data.db.repository;

import com.example.data.db.entity.CarRent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRentRepository extends JpaRepository<CarRent, Long> {
    Optional<CarRent> getCarRentByCarId(Long id);
}
