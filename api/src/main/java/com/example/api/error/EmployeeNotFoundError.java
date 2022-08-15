package com.example.api.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class EmployeeNotFoundError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "This employee is not present!!!";
    }
}
