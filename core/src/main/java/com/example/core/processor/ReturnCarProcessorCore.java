package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.CarNotTakenError;
import com.example.api.error.RentNotFoundError;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import com.example.api.operation.ReturnCarProcessor;
import com.example.data.rentclient.ReturnCarService;
import com.example.data.rentclient.exception.CarNotTakenException;
import com.example.data.rentclient.exception.RentNotFoundException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class ReturnCarProcessorCore implements ReturnCarProcessor {
    private final ReturnCarService returnCarService;

    public ReturnCarProcessorCore(ReturnCarService returnCarService) {
        this.returnCarService = returnCarService;
    }

    @Override
    public Either<Error, ReturnCarResponse> process(ReturnCarRequest returnCarRequest) {
        return Try.of(() -> {
                    returnCarService
                            .returnCar(returnCarRequest);
                return ReturnCarResponse
                        .builder()
                        .output("The car was successfully returned")
                        .build();
            }
        )
        .toEither()
        .mapLeft(throwable -> {
            if(throwable instanceof RentNotFoundException) {
                return new RentNotFoundError();
            }
            if(throwable instanceof CarNotTakenException) {
                return new CarNotTakenError();
            }
            return new RentServiceUnavailable();
        });
    }
}
