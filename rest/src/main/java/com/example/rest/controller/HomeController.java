package com.example.rest.controller;

import com.example.api.base.Error;
import com.example.api.model.*;
import com.example.api.operation.GetCarsProcessor;
import com.example.api.operation.RentCarProcessor;
import com.example.api.operation.ReturnCarProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    public final GetCarsProcessor getCarsProcessor;
    public final RentCarProcessor rentCarProcessor;
    private final ReturnCarProcessor returnCarProcessor;

    public HomeController(GetCarsProcessor getCarsProcessor, RentCarProcessor rentCarProcessor, ReturnCarProcessor returnCarProcessor) {
        this.getCarsProcessor = getCarsProcessor;
        this.rentCarProcessor = rentCarProcessor;
        this.returnCarProcessor = returnCarProcessor;
    }

    @PostMapping("/getCars")
    public ResponseEntity<?> getCarsByStatus(@RequestBody FindCarsRequest findCarsRequest) {
        Either<Error, FindCarsResponse> result = getCarsProcessor.process(findCarsRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/rentCar")
    public ResponseEntity<?> rentACar(@RequestBody RentACarRequest rentACarRequest) {
        Either<Error, RentACarResponse> result = rentCarProcessor.process(rentACarRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/returnCar")
    public ResponseEntity<?> returnCar(@RequestBody ReturnCarRequest returnCarRequest) {
        Either<Error, ReturnCarResponse> result = returnCarProcessor.process(returnCarRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
