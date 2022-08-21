package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.SortEmployeesRequest;
import com.example.api.model.SortEmployeesResponse;
import io.vavr.control.Either;

public interface SortEmployeesProcessor extends OperationProcessor<SortEmployeesRequest, SortEmployeesResponse> {
    Either<Error, SortEmployeesResponse> process(SortEmployeesRequest sortEmployeesRequest);
}
