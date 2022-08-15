package com.example.data.rentclient.mapper;

import com.example.api.operation2.CarEntity;
import org.springframework.stereotype.Service;

@Service
public class CarMapperImpl implements CarMapper{
    @Override
    public CarEntity mapCar(com.example.data.db.entity.Car car) {
        return CarEntity.builder()
                .carId(car.getCarId())
                .price(car.getPrice())
                .status(car.getStatus().toString())
                .vin(car.getVin())
                .build();
    }
}
