package com.example.data.rentclient.implementation;

import com.example.api.ApiFeignInterface;
import com.example.api.operationApi.ApiRequest;
import com.example.api.operationApi.ApiResponse;
import com.example.data.db.repository.CarRepository;
import com.example.data.rentclient.FindCarsService;
import com.example.data.rentclient.mapper.CarMapper;
import feign.FeignException;
import org.springframework.stereotype.Service;
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

    public ApiResponse getCars(String status) {
        try {
            return apiFeignInterface
                    .getCars(ApiRequest
                            .builder()
                            .carEntities(
                                    carRepository
                                            .findAllByStatus(status)
                                            .stream()
                                            .map(carMapper::mapCar)
                                            .collect(Collectors.toList())
                            )
                            .build()
                    );
            } catch(FeignException.FeignClientException e) {
                throw new RuntimeException();
            }
    }
}
