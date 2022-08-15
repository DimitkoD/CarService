package com.example.data.db.repository;

import com.example.data.db.entity.CarRent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentRepository extends JpaRepository<CarRent, Long> {
}
