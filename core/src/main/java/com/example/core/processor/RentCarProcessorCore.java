package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.CarNotFoundError;
import com.example.api.error.PaymentServiceError;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import com.example.api.operation.RentCarProcessor;
import com.example.data.rentclient.RentCarService;
import com.example.data.rentclient.exception.PaymentServiceException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RentCarProcessorCore implements RentCarProcessor {
    private final RentCarService rentCarService;

    public RentCarProcessorCore(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @Override
    public Either<Error, RentACarResponse> process(RentACarRequest input) {
        return Try.of(() -> {
            return RentACarResponse
                .builder()
                .output(
                    rentCarService
                        .rentACar(
                            RentACarRequest
                                .builder()
                                .carVin(input.getCarVin())
                                .cardNumber(input.getCardNumber())
                                .days(input.getDays())
                                .customerId(input.getCustomerId())
                                .employeeId(input.getEmployeeId())
                                .build()
                        )
                    .toString()
                )
                .build();
        })
        .toEither()
        .mapLeft(throwable -> {
            if(throwable instanceof NoSuchElementException) {
                return new CarNotFoundError();
            }
            if(throwable instanceof PaymentServiceException) {
                return new PaymentServiceError();
            }
            return new RentServiceUnavailable();
        });
    }

}
