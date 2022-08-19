package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.SortCarsRequest;
import com.example.api.model.SortCarsResponse;
import io.vavr.control.Either;


public interface SortCarsProcessor extends OperationProcessor<SortCarsRequest, SortCarsResponse> {
    Either<Error, SortCarsResponse> process(SortCarsRequest sortCarsRequest);
}
