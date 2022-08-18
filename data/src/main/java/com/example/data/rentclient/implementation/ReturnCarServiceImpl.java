package com.example.data.rentclient.implementation;

import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.entity.CarRent;
import com.example.data.db.entity.Customer;
import com.example.data.db.repository.CarRentRepository;
import com.example.data.db.repository.CarRepository;
import com.example.data.db.repository.CustomerRepository;
import com.example.data.rentclient.ReturnCarService;
import com.example.data.rentclient.exception.CarNotTakenException;
import com.example.data.rentclient.exception.RentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
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

    public Boolean returnCar(ReturnCarRequest returnCarRequest) {

        return
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
    }
}
