package com.example.api.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class PaymentServiceError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    @Override
    public String getMessage() {
        return "Payment service not responding!!!";
    }
}
