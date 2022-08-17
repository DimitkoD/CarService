package com.example.data.rentclient.implementation;

import com.example.api.ApiFeignInterface;
import com.example.api.operationApi.ApiRequest;
import com.example.api.operationApi.ApiResponse;
import com.example.data.db.entity.Car;
import com.example.data.db.repository.CarRepository;
import com.example.data.rentclient.FindCarsService;
import com.example.data.rentclient.exception.ApiServiceException;
import com.example.data.rentclient.exception.ApiServiceNotRespondingException;
import com.example.data.rentclient.exception.CarNotFoundException;
import com.example.data.rentclient.mapper.CarMapper;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindCarsServiceImpl implements FindCarsService {
    private final CarRepository carRepository;
    private final ApiFeignInterface apiFeignInterface;
    private final CarMapper carMapper;

    public FindCarsServiceImpl(CarRepository carRepository, ApiFeignInterface apiFeignInterface, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.apiFeignInterface = apiFeignInterface;
        this.carMapper = carMapper;
    }

    public ApiResponse getCars(Boolean status) {

        List<Car> cars =  carRepository
                .findAllByStatus(status);

        if(cars.isEmpty()){
            throw new CarNotFoundException();
        }

        try {

            return apiFeignInterface
                    .getCars(ApiRequest
                            .builder()
                            .carEntities(cars
                                            .stream()
                                            .map(carMapper::mapCar)
                                            .collect(Collectors.toList())
                            )
                            .build()
                    );
        }
        catch(RetryableException e) {
            throw new ApiServiceNotRespondingException();
        }
        catch (FeignException.FeignClientException e) {
            throw new ApiServiceException();
        }
    }
}
