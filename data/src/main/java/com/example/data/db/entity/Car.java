package com.example.data.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String vin;
    private double price;
    private Boolean status;

    @OneToMany(mappedBy = "car")
    private Set<CarRent> carRentEntities;
}
