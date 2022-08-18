package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.ApiServiceError;
import com.example.api.error.ApiServiceNotRespondingError;
import com.example.api.error.CarNotFoundError;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.model.FindCarsResponse;
import com.example.api.model.FindCarsRequest;
import com.example.api.operation.GetCarsInfoProcessor;
import com.example.data.rentclient.FindCarsService;
import com.example.data.rentclient.exception.CarNotFoundException;
import feign.FeignException;
import feign.RetryableException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class GetCarsInfoProcessorCore implements GetCarsInfoProcessor {

    private final FindCarsService findCarsService;
    public GetCarsInfoProcessorCore(FindCarsService findCarsService ) {
        this.findCarsService = findCarsService;
    }

    @Override
    public Either<Error, FindCarsResponse> process(FindCarsRequest findCarsRequest) {
        return Try.of(() -> {
           return FindCarsResponse
                   .builder()
                   .carsAvailable(
                           findCarsService
                                   .getCars(findCarsRequest.getStatus())
                                   .getCarsAvailable()
                   )
                   .build();
        })
        .toEither()
        .mapLeft(throwable -> {
            if(throwable instanceof CarNotFoundException) {
                return new CarNotFoundError();
            }
            if(throwable instanceof RetryableException) {
                return new ApiServiceNotRespondingError();
            }
            if(throwable instanceof FeignException.FeignClientException) {
                return new ApiServiceError();
            }
            return new RentServiceUnavailable();
        });
    }
}
