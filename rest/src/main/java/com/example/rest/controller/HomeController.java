package com.example.rest.controller;

import com.example.api.base.Error;
import com.example.api.model.FindCarsResponse;
import com.example.api.model.FindCarsRequest;
import com.example.api.operation.GetCarsProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    public final GetCarsProcessor getCarsProcessor;

    public HomeController(GetCarsProcessor getCarsProcessor) {
        this.getCarsProcessor = getCarsProcessor;
    }

    @PostMapping("/cars")
    public ResponseEntity<?> getCarsByStatus(@RequestBody FindCarsRequest findCarsRequest) {
        Either<Error, FindCarsResponse> result = getCarsProcessor.process(findCarsRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
