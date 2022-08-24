package com.example.api.error;

import com.example.api.base.Error;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode
public class RentsNotFoundError implements Error {

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "There are no rents made!!!";
    }
}
