package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.FindCarsRequest;
import com.example.api.model.FindCarsResponse;
import io.vavr.control.Either;


public interface GetCarsInfoProcessor extends OperationProcessor<FindCarsRequest, FindCarsResponse> {
    Either<Error, FindCarsResponse> process(FindCarsRequest findCarsRequest);
}
