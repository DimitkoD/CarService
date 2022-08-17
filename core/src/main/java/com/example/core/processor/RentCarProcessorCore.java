package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.*;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import com.example.api.operation.RentCarProcessor;
import com.example.data.rentclient.RentCarService;
import com.example.data.rentclient.exception.*;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class RentCarProcessorCore implements RentCarProcessor {
    private final RentCarService rentCarService;

    public RentCarProcessorCore(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @Override
    public Either<Error, RentACarResponse> process(RentACarRequest input) {
        return Try.of(() -> {
            return rentCarService
                        .rentACar(
                            RentACarRequest
                                .builder()
                                .carVin(input.getCarVin())
                                .cardNumber(input.getCardNumber())
                                .days(input.getDays())
                                .customerId(input.getCustomerId())
                                .employeeId(input.getEmployeeId())
                                .build()
                        );
        })
        .toEither()
        .mapLeft(throwable -> {

            if(throwable instanceof PaymentServiceNotRespondingException) {
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
            if(throwable instanceof PaymentServiceException) {
                return new PaymentServiceError();
            }

            return new RentServiceUnavailable();
        });
    }

}
