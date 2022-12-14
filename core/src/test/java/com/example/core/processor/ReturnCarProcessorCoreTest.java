package com.example.core.processor;

import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.entity.Employee;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReturnCarProcessorCoreTest {
    @Mock
    private CarRentRepository carRentRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private ReturnCarProcessorCore returnCarProcessorCore;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void returnCarTest() {
        Car car1 = Car
                .builder()
                .carId(1L)
                .vin("3G4AG55M8RS622999")
                .price(25.0)
                .status(true)
                .build();

        Customer customer1 = Customer
                .builder()
                .id(1L)
                .fullName("Petko Ivanov")
                .customerStatus(true)
                .build();

        Employee employee1 = Employee
                .builder()
                .id(1L)
                .fullName("Petko Ivanov")
                .positionId(1L)
                .build();

        ReturnCarRequest returnCarRequest = ReturnCarRequest
                .builder()
                .carId(car1.getCarId())
                .build();

        CarRent carRent1 = CarRent
                .builder()
                .id(1L)
                .carId(car1.getCarId())
                .car(car1)
                .customerId(customer1.getId())
                .customer(customer1)
                .employeeId(employee1.getId())
                .employee(employee1)
                .price(200.0)
                .days(5)
                .date(LocalDate.now())
                .build();

        CarRent carRent2 = CarRent
                .builder()
                .id(2L)
                .carId(car1.getCarId())
                .customerId(customer1.getId())
                .employeeId(employee1.getId())
                .price(250.0)
                .days(6)
                .date(LocalDate.now())
                .build();

        CarRent carRent3 = CarRent
                .builder()
                .id(3L)
                .carId(car1.getCarId())
                .customerId(customer1.getId())
                .employeeId(employee1.getId())
                .price(150.0)
                .days(4)
                .date(LocalDate.now())
                .build();

        ReturnCarResponse returnCarResponse = ReturnCarResponse
                .builder()
                .output("The car was successfully returned")
                .build();

        Mockito.when(carRentRepository.getCarRentsByCarId(returnCarRequest.getCarId()))
                .thenReturn(List.of(carRent1, carRent2, carRent3));
        Mockito.when(carRentRepository.save(carRent1))
                .thenReturn(null);


        Mockito.when(customerRepository.save(customer1))
                .thenReturn(null);

        Assertions.assertEquals(returnCarResponse, returnCarProcessorCore.process(returnCarRequest).get());

    }
}