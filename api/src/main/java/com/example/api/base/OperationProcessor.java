package com.example.api.base;

import io.vavr.control.Either;

public interface OperationProcessor<I, R> {
    Either<Error, R> process(I input);
}
