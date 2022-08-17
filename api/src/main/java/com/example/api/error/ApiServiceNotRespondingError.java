package com.example.api.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class ApiServiceNotRespondingError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    @Override
    public String getMessage() {
        return "Api service not responding!!!";
    }
}
