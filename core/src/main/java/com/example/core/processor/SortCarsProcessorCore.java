package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.error.RentsNotFoundError;
import com.example.api.model.SortCarsRequest;
import com.example.api.model.SortCarsResponse;
import com.example.api.operation.SortCarsProcessor;
import com.example.core.exception.RentsNotFoundException;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SortCarsProcessorCore implements SortCarsProcessor {

    private final CarRentRepository carRentRepository;
    private final CarRepository carRepository;

    public SortCarsProcessorCore(CarRentRepository carRentRepository, CarRepository carRepository) {
        this.carRentRepository = carRentRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Either<Error, SortCarsResponse> process(SortCarsRequest sortCarsRequest) {
        return Try.of(() -> {
            return SortCarsResponse
                    .builder()
                    .sortedCarsByTimesRented(
                            carRepository
                                    .findAll()
                                    .stream()
                                    .collect(Collectors
                                            .toMap(
                                                    Car::getVin,
                                                    car -> {
                                                        List<CarRent> carRents = carRentRepository
                                                                .getCarRentByCarId(car.getCarId());

                                                        if(carRents.isEmpty()) {
                                                            throw new RentsNotFoundException();
                                                        }
                                                        return carRents.size();
                                                    }
                                            )
                                    )
                                    .entrySet()
                                    .stream()
                                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                    .collect(Collectors.toMap(
                                                Map.Entry::getKey, Map.Entry::getValue,
                                                (e1, e2) -> e1, LinkedHashMap::new
                                            )
                                    )
                    )
                    .build();

        })
        .toEither()
        .mapLeft(throwable -> {
            if(throwable instanceof RentsNotFoundException) {
                return new RentsNotFoundError();
            }
            return new RentServiceUnavailable();
        });
    }
}
