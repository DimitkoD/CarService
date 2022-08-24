package com.example.core.processor;

import com.example.api.base.Error;
import com.example.api.error.RentServiceUnavailable;
import com.example.api.error.RentsNotFoundError;
import com.example.api.model.SortEmployeesRequest;
import com.example.api.model.SortEmployeesResponse;
import com.example.api.operation.SortEmployeesProcessor;
import com.example.core.exception.RentsNotFoundException;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Employee;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.EmployeeRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SortEmployeesProcessorCore implements SortEmployeesProcessor {
    private final EmployeeRepository employeeRepository;
    private final CarRentRepository carRentRepository;

    public SortEmployeesProcessorCore(EmployeeRepository employeeRepository, CarRentRepository carRentRepository) {
        this.employeeRepository = employeeRepository;
        this.carRentRepository = carRentRepository;
    }

    @Override
    public Either<Error, SortEmployeesResponse> process(SortEmployeesRequest sortEmployeesRequest) {
        return Try.of(() -> {
            List<CarRent> rents = carRentRepository.findAll();

            if(rents.isEmpty()) {
                throw new RentsNotFoundException();
            }

            return SortEmployeesResponse
                    .builder()
                    .sortedEmployeesByTimesRented(
                            employeeRepository
                                    .findAll()
                                    .stream()
                                    .collect(Collectors
                                            .toMap(
                                                    Employee::getFullName,
                                                    employee -> {
                                                        List<CarRent> carRents = carRentRepository
                                                                .getCarRentsByEmployeeId(employee.getId());

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
