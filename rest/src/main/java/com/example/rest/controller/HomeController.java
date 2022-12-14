package com.example.rest.controller;

import com.example.api.base.Error;
import com.example.api.model.*;
import com.example.api.operation.*;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class HomeController {
    public final GetCarsInfoProcessor getCarsInfoProcessor;
    public final RentCarProcessor rentCarProcessor;
    private final ReturnCarProcessor returnCarProcessor;
    private final SortCarsProcessor sortCarsProcessor;
    private final SortEmployeesProcessor sortEmployeesProcessor;

    public HomeController(GetCarsInfoProcessor getCarsInfoProcessor,
                          RentCarProcessor rentCarProcessor,
                          ReturnCarProcessor returnCarProcessor,
                          SortCarsProcessor sortCarsProcessor,
                          SortEmployeesProcessor sortEmployeesProcessor) {

        this.getCarsInfoProcessor = getCarsInfoProcessor;
        this.rentCarProcessor = rentCarProcessor;
        this.returnCarProcessor = returnCarProcessor;
        this.sortCarsProcessor = sortCarsProcessor;
        this.sortEmployeesProcessor = sortEmployeesProcessor;
    }

    @PostMapping("/getCars")
    public ResponseEntity<?> getCarsByStatus(@RequestBody FindCarsRequest findCarsRequest) {
        Either<Error, FindCarsResponse> result = getCarsInfoProcessor.process(findCarsRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/rentCar")
    public ResponseEntity<?> rentACar(@Valid @RequestBody RentACarRequest rentACarRequest) {
        Either<Error, RentACarResponse> result = rentCarProcessor.process(rentACarRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/returnCar")
    public ResponseEntity<?> returnCar(@Valid @RequestBody ReturnCarRequest returnCarRequest) {
        Either<Error, ReturnCarResponse> result = returnCarProcessor.process(returnCarRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/sortCarsByTimesRented")
    public ResponseEntity<?> sortCarsByTimesRented(@RequestBody SortCarsRequest sortCarsRequest) {
        Either<Error, SortCarsResponse> result = sortCarsProcessor.process(sortCarsRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/sortEmployeesByTimesRented")
    public ResponseEntity<?> sortEmployeesByTimesRented(@RequestBody SortEmployeesRequest sortEmployeesRequest) {
        Either<Error, SortEmployeesResponse> result = sortEmployeesProcessor.process(sortEmployeesRequest);
        if(result.isLeft()){
            return ResponseEntity
                    .status(result.getLeft().getCode())
                    .body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
