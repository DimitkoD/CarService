package com.example.core.processor;

import com.example.api.error.RentsNotFoundError;
import com.example.api.model.*;
import com.example.core.exception.RentsNotFoundException;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.entity.Employee;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.EmployeeRepository;
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

class SortEmployeesProcessorCoreTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CarRentRepository carRentRepository;
    @InjectMocks
    private SortEmployeesProcessorCore sortEmployeesProcessorCore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sortTest() {
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

        Employee employee2 = Employee
                .builder()
                .id(2L)
                .fullName("Ivan Ivanov")
                .positionId(2L)
                .build();

        Employee employee3 = Employee
                .builder()
                .id(3L)
                .fullName("Georgi Petkov")
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

        Mockito.when(carRentRepository.findAll())
                .thenReturn(List.of(carRent1, carRent2, carRent3));

        Mockito.when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2, employee3));

        Mockito.when(carRentRepository.getCarRentsByEmployeeId(employee1.getId()))
                .thenReturn(List.of(carRent1, carRent2, carRent3));

        SortEmployeesRequest sortEmployeesRequest = SortEmployeesRequest
                .builder()
                .build();

        Map<String, Integer> sortedEmployees = new HashMap<>();
        sortedEmployees.put(employee1.getFullName(), 3);
        sortedEmployees.put(employee2.getFullName(), 0);
        sortedEmployees.put(employee3.getFullName(), 0);

        SortEmployeesResponse sortEmployeesResponse = SortEmployeesResponse
                .builder()
                .sortedEmployeesByTimesRented(sortedEmployees)
                .build();

        Assertions.assertEquals(sortEmployeesResponse, sortEmployeesProcessorCore.process(sortEmployeesRequest).get());

    }

    @Test
    void exceptionTest() {
        SortEmployeesRequest sortEmployeesRequest = SortEmployeesRequest
                .builder()
                .build();

        RentsNotFoundException rentsNotFoundException = new RentsNotFoundException();
        RentsNotFoundError rentsNotFoundError = new RentsNotFoundError();


        Mockito.when(carRentRepository.findAll())
                .thenThrow(rentsNotFoundException);


        Assertions.assertEquals(rentsNotFoundError, sortEmployeesProcessorCore.process(sortEmployeesRequest).getLeft());
    }
}