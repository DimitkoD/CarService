package com.example.data.rentclient.implementation;

import com.example.api.PaymentFeignInterface;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import com.example.api.operationPayment.PaymentServiceRequest;
import com.example.api.operationPayment.PaymentServiceResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.EmployeeRepository;
import com.example.data.db.repository.PersonRepository;
import com.example.data.rentclient.PriceService;
import com.example.data.rentclient.RentCarService;
import com.example.data.rentclient.exception.PaymentServiceException;
import feign.FeignException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RentCarServiceImpl implements RentCarService {
    private final PaymentFeignInterface paymentFeignInterface;
    private final CarRepository carRepository;
    private final PriceService priceService;
    private final CarRentRepository carRentRepository;
    private final PersonRepository personRepository;
    private final EmployeeRepository employeeRepository;

    public RentCarServiceImpl(PaymentFeignInterface paymentFeignInterface, CarRepository carRepository, PriceService priceService, CarRentRepository carRentRepository, PersonRepository personRepository, EmployeeRepository employeeRepository) {
        this.paymentFeignInterface = paymentFeignInterface;
        this.carRepository = carRepository;
        this.priceService = priceService;
        this.carRentRepository = carRentRepository;
        this.personRepository = personRepository;
        this.employeeRepository = employeeRepository;
    }

    public RentACarResponse rentACar(RentACarRequest rentACarRequest) {

        try {
            double totalRentPrice = priceService
                    .getPrice(rentACarRequest.getDays(),
                            carRepository
                                    .findCarByStatusAndVin("false",
                                            rentACarRequest.getCarVin())
                                    .orElseThrow(NoSuchElementException::new)
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
                        ))
                        .peek(x -> {
                            if(x.getResponseStatus().equals(200)) {
                               carRentRepository
                                   .save(CarRent
                                       .builder()
                                           .carId(carRepository
                                               .findCarByVin(rentACarRequest.getCarVin())
                                               .orElseThrow(NoSuchElementException::new)
                                               .getCarId()
                                           )
                                           .customerId(personRepository
                                                        .getCustomerById(rentACarRequest.getCustomerId())
                                                   .orElseThrow(NoSuchElementException::new)
                                                   .getId()
                                           )
                                           .employeeId(employeeRepository
                                                   .getEmployeeById(rentACarRequest.getEmployeeId())
                                                   .orElseThrow(NoSuchElementException::new)
                                                   .getId()
                                           )
                                           .date(LocalDate.now())
                                           .days(rentACarRequest.getDays())
                                           .price(totalRentPrice)
                                       .build()
                                   );
                            }
                            Car carToUpdate = carRepository
                                    .findCarByVin(rentACarRequest.getCarVin())
                                    .orElseThrow(NoSuchElementException::new);
                            carToUpdate.setStatus("true");
                            carRepository
                                    .save(carToUpdate);
                        })
                        .map(PaymentServiceResponse::getMessage)
                        .collect(Collectors.joining())
                )
                .build();
        }
        catch (FeignException.FeignClientException e) {
            throw new PaymentServiceException();
        }
        catch (NoSuchElementException e) {
            throw new RuntimeException();
        }
    }
}
