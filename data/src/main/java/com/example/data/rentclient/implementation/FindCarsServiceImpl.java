package com.example.data.rentclient.implementation;

import com.example.api.ApiFeignInterface;
import com.example.api.model.GetCarsRequest;
import com.example.api.model.GetCarsResponse;
import com.example.data.db.entity.Car;
import com.example.data.rentclient.FindCarsService;
import com.example.data.rentclient.mapper.CarMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindCarsServiceImpl implements FindCarsService {
    private final ApiFeignInterface apiFeignInterface;
    private final CarMapper carMapper;

    public FindCarsServiceImpl(ApiFeignInterface apiFeignInterface, CarMapper carMapper) {
        this.apiFeignInterface = apiFeignInterface;
        this.carMapper = carMapper;
    }

    public GetCarsResponse getCars(List<Car> cars) {

        return apiFeignInterface
                .getCars(GetCarsRequest
                        .builder()
                        .carEntities(cars
                                        .stream()
                                        .map(carMapper::mapCar)
                                        .collect(Collectors.toList())
                        )
                        .build()
                );

    }
}
