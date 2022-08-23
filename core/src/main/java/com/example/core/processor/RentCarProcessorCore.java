package com.example.core.processor;

import com.example.api.PaymentFeignInterface;
import com.example.api.base.Error;
import com.example.api.error.*;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import com.example.api.operation.RentCarProcessor;
import com.example.api.operationPayment.PaymentServiceRequest;
import com.example.api.operationPayment.PaymentServiceResponse;
import com.example.core.exception.CarNotFoundException;
import com.example.core.exception.CustomerHasAlreadyRentedException;
import com.example.core.exception.CustomerNotFoundException;
import com.example.core.exception.EmployeeNotFoundException;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import com.example.data.db.repository.EmployeeRepository;
import com.example.data.rentclient.PriceService;
import feign.FeignException;
import feign.RetryableException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RentCarProcessorCore implements RentCarProcessor {
    private final PaymentFeignInterface paymentFeignInterface;
    private final CarRepository carRepository;
    private final PriceService priceService;
    private final CarRentRepository carRentRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public RentCarProcessorCore(PaymentFeignInterface paymentFeignInterface, CarRepository carRepository, PriceService priceService, CarRentRepository carRentRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.paymentFeignInterface = paymentFeignInterface;
        this.carRepository = carRepository;
        this.priceService = priceService;
        this.carRentRepository = carRentRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Either<Error, RentACarResponse> process(RentACarRequest rentACarRequest) {
        return Try.of(() -> {
                    double totalRentPrice = priceService
                            .getPrice(rentACarRequest.getDays(),
                                    carRepository
                                            .findCarByStatusAndVin(false,
                                                    rentACarRequest.getCarVin())
                                            .orElseThrow(CarNotFoundException::new)
                                            .getPrice()
                            );
                    return RentACarResponse
                            .builder()
                            .output(
                                    Stream.of(paymentFeignInterface
                                                    .pay(PaymentServiceRequest
                                                            .builder()
                                                            .cardNumber(rentACarRequest.getCardNumber())
                                                            .totalPriceForRent(totalRentPrice)
                                                            .build()
                                                    )
                                            )
                                            .peek(x -> {
                                                if(customerRepository
                                                        .getCustomerById(rentACarRequest.getCustomerId())
                                                        .orElseThrow()
                                                        .getCustomerStatus()
                                                ) {
                                                    throw new CustomerHasAlreadyRentedException();
                                                }

                                                if(x.getResponseStatus().equals(200)) {
                                                    carRentRepository
                                                            .save(CarRent
                                                                    .builder()
                                                                    .carId(carRepository
                                                                            .findCarByVin(rentACarRequest.getCarVin())
                                                                            .orElseThrow(CarNotFoundException::new)
                                                                            .getCarId()
                                                                    )
                                                                    .customerId(customerRepository
                                                                            .getCustomerById(rentACarRequest.getCustomerId())
                                                                            .orElseThrow(CustomerNotFoundException::new)
                                                                            .getId()
                                                                    )
                                                                    .employeeId(employeeRepository
                                                                            .getEmployeeById(rentACarRequest.getEmployeeId())
                                                                            .orElseThrow(EmployeeNotFoundException::new)
                                                                            .getId()
                                                                    )
                                                                    .date(LocalDate.now())
                                                                    .days(rentACarRequest.getDays())
                                                                    .price(totalRentPrice)
                                                                    .build()
                                                            );

                                                    Car carToUpdate = carRepository
                                                            .findCarByVin(rentACarRequest.getCarVin())
                                                            .orElseThrow(CarNotFoundException::new);
                                                    carToUpdate.setStatus(true);
                                                    carRepository
                                                            .save(carToUpdate);

                                                    Customer customerToUpdate =  customerRepository
                                                            .getCustomerById(rentACarRequest.getCustomerId())
                                                            .orElseThrow(CustomerNotFoundException::new);
                                                    customerToUpdate.setCustomerStatus(true);
                                                    customerRepository
                                                            .save(customerToUpdate);
                                                }
                                            })
                                            .map(PaymentServiceResponse::getMessage)
                                            .collect(Collectors.joining())
                            )
                            .build();
        })
        .toEither()
        .mapLeft(throwable -> {

            if(throwable instanceof RetryableException) {
                return new PaymentServiceNotRespondingError();
            }
            if(throwable instanceof CarNotFoundException) {
                return new CarNotFoundError();
            }
            if(throwable instanceof EmployeeNotFoundException) {
                return new EmployeeNotFoundError();
            }
            if(throwable instanceof CustomerNotFoundException) {
                return new CustomerNotFoundError();
            }
            if(throwable instanceof CustomerHasAlreadyRentedException){
                return new CustomerHasAlreadyRentedError();
            }
            if(throwable instanceof FeignException.FeignClientException) {
                return new PaymentServiceError();
            }

            return new RentServiceUnavailable();
        });
    }

}
