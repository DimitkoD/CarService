package com.example.data.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private Boolean customerStatus;

    @OneToMany(mappedBy = "customer")
    private Set<CarRent> carRentEntities;
}
