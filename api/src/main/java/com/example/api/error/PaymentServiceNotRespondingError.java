package com.example.api.error;

import com.example.api.base.Error;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode
public class PaymentServiceNotRespondingError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    @Override
    public String getMessage() {
        return "Payment service not responding!!!";
    }
}
