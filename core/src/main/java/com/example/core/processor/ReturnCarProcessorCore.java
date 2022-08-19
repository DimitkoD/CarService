package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.CarNotTakenError;
import com.example.api.error.RentNotFoundError;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import com.example.api.operation.ReturnCarProcessor;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import com.example.core.exception.CarNotTakenException;
import com.example.core.exception.RentNotFoundException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ReturnCarProcessorCore implements ReturnCarProcessor {
    private final CarRentRepository carRentRepository;
    private final CustomerRepository customerRepository;

    private final CarRepository carRepository;

    public ReturnCarProcessorCore(CarRentRepository carRentRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.carRentRepository = carRentRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }


    @Override
    public Either<Error, ReturnCarResponse> process(ReturnCarRequest returnCarRequest) {
        return Try.of(() -> {
                            Stream.of(carRentRepository)
                                    .map(carRentRepository -> {

                                                List<CarRent> carRents = carRentRepository
                                                        .getCarRentByCarId(
                                                                returnCarRequest.getCarId()
                                                        );

                                                if(carRents.isEmpty()) {
                                                    throw new RentNotFoundException();
                                                }

                                                CarRent foundCarRent = carRents
                                                        .stream()
                                                        .filter(carRent -> carRent.getCar().getStatus().equals(true))
                                                        .findFirst()
                                                        .orElseThrow(CarNotTakenException::new);

                                                Car carToReturn = foundCarRent.getCar();

                                                carToReturn.setStatus(false);
                                                carRepository.save(carToReturn);

                                                Customer customerToUpdate = foundCarRent.getCustomer();
                                                customerToUpdate.setCustomerStatus(false);
                                                customerRepository.save(customerToUpdate);

                                                return true;
                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(RentNotFoundException::new);

                return ReturnCarResponse
                        .builder()
                        .output("The car was successfully returned")
                        .build();
            }
        )
        .toEither()
        .mapLeft(throwable -> {
            if(throwable instanceof RentNotFoundException) {
                return new RentNotFoundError();
            }
            if(throwable instanceof CarNotTakenException) {
                return new CarNotTakenError();
            }
            return new RentServiceUnavailable();
        });
    }
}
