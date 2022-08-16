package com.example.data.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rents")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long carId;
    private Long customerId;
    private Long employeeId;
    private Integer days;
    private Double price;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "carId", insertable = false, updatable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private Customer customer;
}
