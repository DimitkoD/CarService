package com.example.data.rentclient.implementation;

import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import com.example.data.rentclient.ReturnCarService;
import com.example.data.rentclient.exception.RentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ReturnCarServiceImpl implements ReturnCarService {
    private final CarRentRepository carRentRepository;
    private final CustomerRepository customerRepository;

    private final CarRepository carRepository;

    public ReturnCarServiceImpl(CarRentRepository carRentRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.carRentRepository = carRentRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public ReturnCarResponse returnCar(ReturnCarRequest returnCarRequest) {

        return ReturnCarResponse
                .builder()
                .output(
                        Stream.of(carRentRepository)
                                .map(x -> x
                                        .getCarRentByCarId(returnCarRequest.getCarId())
                                        .orElseThrow(RentNotFoundException::new))
                                .map(carRent ->
                                   carRepository
                                            .findCarByVin(carRent.getCar().getVin())
                                            .orElseThrow())
                                .toList()
                                .toString()
                )
                .build();
    }
}
