package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import io.vavr.control.Either;

public interface ReturnCarProcessor extends OperationProcessor<ReturnCarRequest, ReturnCarResponse> {
    Either<Error, ReturnCarResponse> process(ReturnCarRequest returnCarRequest);
}
