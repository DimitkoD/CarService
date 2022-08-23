package com.example.core.processor;

import com.example.api.PaymentFeignInterface;
import com.example.api.base.Error;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import com.example.api.operationPayment.PaymentServiceRequest;
import com.example.api.operationPayment.PaymentServiceResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.entity.Employee;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import com.example.data.db.repository.EmployeeRepository;
import com.example.data.rentclient.PriceService;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RentCarProcessorCoreTest {
    @Mock
    private PaymentFeignInterface paymentFeignInterface;
    @Mock
    private CarRepository carRepository;
    @Mock
    private PriceService priceService;
    @Mock
    private CarRentRepository carRentRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private RentCarProcessorCore rentCarProcessorCore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void rentTes() {

        Car car1 = Car
                .builder()
                .carId(1L)
                .vin("3G4AG55M8RS622999")
                .price(25.0)
                .status(false)
                .build();

        Customer customer1 = Customer
                .builder()
                .id(1L)
                .fullName("Petko Ivanov")
                .customerStatus(false)
                .build();

        Employee employee1 = Employee
                .builder()
                .id(1L)
                .fullName("Petko Ivanov")
                .positionId(1L)
                .build();

        RentACarRequest rentACarRequest = RentACarRequest
                .builder()
                .carVin(car1.getVin())
                .cardNumber("1234567891234567")
                .days(2)
                .customerId(customer1.getId())
                .employeeId(employee1.getId())
                .build();

        Mockito.when(carRepository.findCarByStatusAndVin(false, car1.getVin()))
                .thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findCarByVin(car1.getVin()))
                .thenReturn(Optional.of(car1));

        Mockito.when(customerRepository.getCustomerById(rentACarRequest.getCustomerId()))
                .thenReturn(Optional.of(customer1));

        Mockito.when(employeeRepository.getEmployeeById(rentACarRequest.getCustomerId()))
                .thenReturn(Optional.of(employee1));

        Mockito.when(priceService.getPrice(rentACarRequest.getDays(), car1.getPrice()))
                .thenReturn(50.0);

        CarRent rent1 = CarRent
                .builder()
                .id(1L)
                .carId(car1.getCarId())
                .customerId(rentACarRequest.getCustomerId())
                .employeeId(rentACarRequest.getEmployeeId())
                .date(LocalDate.now())
                .days(rentACarRequest.getDays())
                .price(priceService.getPrice(rentACarRequest.getDays(), car1.getPrice()))
                .build();

        Mockito.when(carRentRepository.save(rent1)).thenReturn(null);

        Mockito.when(customerRepository.save(customer1)).thenReturn(null);

        Mockito.when(carRepository.save(car1)).thenReturn(null);


        PaymentServiceRequest paymentServiceRequest = PaymentServiceRequest
                .builder()
                .cardNumber(rentACarRequest.getCardNumber())
                .totalPriceForRent(priceService.getPrice(rentACarRequest.getDays(), car1.getPrice()))
                .build();

        PaymentServiceResponse feignResponse = PaymentServiceResponse
                .builder()
                .responseStatus(200)
                .message("Payment is successful!!!")
                .build();

        Mockito.when(paymentFeignInterface.pay(paymentServiceRequest))
        .thenReturn(feignResponse);

        RentACarResponse rentACarResponse = RentACarResponse
                .builder()
                .output("Payment is successful!!!")
                .build();

        Assertions.assertEquals(feignResponse, paymentFeignInterface.pay(paymentServiceRequest));

        //Assertions.assertNotNull(rentCarProcessorCore.process(rentACarRequest).get());
        Assertions.assertEquals(rentACarResponse, rentCarProcessorCore.process(rentACarRequest).get());

    }

}