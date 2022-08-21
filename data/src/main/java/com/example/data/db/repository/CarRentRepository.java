package com.example.data.db.repository;

import com.example.data.db.entity.CarRent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRentRepository extends JpaRepository<CarRent, Long> {
    List<CarRent> getCarRentsByCarId(Long id);
    List<CarRent> getCarRentsByEmployeeId(Long id);
    List<CarRent> findAll();
}
