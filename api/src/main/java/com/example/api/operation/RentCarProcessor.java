package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;
import io.vavr.control.Either;

public interface RentCarProcessor extends OperationProcessor<RentACarRequest, RentACarResponse> {
    Either<Error, RentACarResponse> process(RentACarRequest rentACarRequest);
}
