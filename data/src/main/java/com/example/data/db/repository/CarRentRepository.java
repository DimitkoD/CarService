package com.example.data.db.repository;

import com.example.data.db.entity.CarRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface CarRentRepository extends JpaRepository<CarRent, Long> {
    List<CarRent> getCarRentsByCarId(Long id);
    List<CarRent> getCarRentsByEmployeeId(Long id);
    List<CarRent> findAll();
}
