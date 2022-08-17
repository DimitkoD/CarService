package com.example.api.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "This person does not exist!!!";
    }
}
