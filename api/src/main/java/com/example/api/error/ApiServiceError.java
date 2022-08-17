package com.example.api.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class ApiServiceError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "Api Service Internal Error!!!";
    }
}
