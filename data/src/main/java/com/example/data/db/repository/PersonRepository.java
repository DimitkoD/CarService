package com.example.data.db.repository;

import com.example.data.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Customer, Long> {
}
