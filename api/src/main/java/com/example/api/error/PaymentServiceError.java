package com.example.api.error;

import com.example.api.base.Error;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode
public class PaymentServiceError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "Payment Service Internal Error!!!";
    }
}
