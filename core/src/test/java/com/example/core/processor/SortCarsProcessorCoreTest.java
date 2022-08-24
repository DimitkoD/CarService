package com.example.core.processor;

import com.example.api.error.RentsNotFoundError;
import com.example.api.model.*;
import com.example.core.exception.RentNotFoundException;
import com.example.core.exception.RentsNotFoundException;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.entity.Employee;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SortCarsProcessorCoreTest {
    @Mock
    private CarRentRepository carRentRepository;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private SortCarsProcessorCore sortCarsProcessorCore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setSortTest() {
        Car car1 = Car
                .builder()
                .carId(1L)
                .vin("3G4AG55M8RS622999")
                .price(25.0)
                .status(true)
                .build();

        Car car2 = Car
                .builder()
                .carId(2L)
                .vin("3G4AG55M8RS622888")
                .price(45.0)
                .status(true)
                .build();

        Car car3 = Car
                .builder()
                .carId(3L)
                .vin("3G4AG55M8RS622777")
                .price(35.0)
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
                .customer(customer1)
                .employeeId(employee1.getId())
                .employee(employee1)
                .price(250.0)
                .days(6)
                .date(LocalDate.now())
                .build();

        CarRent carRent3 = CarRent
                .builder()
                .id(3L)
                .carId(car1.getCarId())
                .customerId(customer1.getId())
                .customer(customer1)
                .employeeId(employee1.getId())
                .employee(employee1)
                .price(150.0)
                .days(4)
                .date(LocalDate.now())
                .build();

        Mockito.when(carRentRepository.findAll())
                .thenReturn(List.of(carRent1, carRent2, carRent3));

        Mockito.when(carRentRepository.getCarRentsByCarId(car1.getCarId()))
                .thenReturn(List.of(carRent1, carRent2, carRent3));

        Mockito.when(carRepository.findAll())
                .thenReturn(List.of(car1, car2, car3));

        SortCarsRequest sortCarsRequest = SortCarsRequest
                .builder()
                .build();

        Map<String, Integer> sortedCars = new HashMap<>();
        sortedCars.put(car1.getVin(), 3);
        sortedCars.put(car2.getVin(), 0);
        sortedCars.put(car3.getVin(), 0);

        SortCarsResponse sortCarsResponse = SortCarsResponse
                .builder()
                .sortedCarsByTimesRented(sortedCars)
                .build();

        Assertions.assertEquals(sortCarsResponse, sortCarsProcessorCore.process(sortCarsRequest).get());
    }

    @Test
    void exceptionTest() {
        SortCarsRequest sortCarsRequest = SortCarsRequest
                .builder()
                .build();

        RentsNotFoundException rentsNotFoundException = new RentsNotFoundException();
        RentsNotFoundError rentsNotFoundError = new RentsNotFoundError();


        Mockito.when(carRentRepository.findAll())
                .thenThrow(rentsNotFoundException);


        Assertions.assertEquals(rentsNotFoundError, sortCarsProcessorCore.process(sortCarsRequest).getLeft());
    }
}