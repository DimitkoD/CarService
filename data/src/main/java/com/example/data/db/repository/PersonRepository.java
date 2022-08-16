package com.example.data.db.repository;

import com.example.data.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerById(Long id);
}
