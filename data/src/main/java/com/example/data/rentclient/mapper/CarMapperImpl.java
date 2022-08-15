package com.example.data.rentclient.mapper;

import com.example.api.operationApi.CarApiEntity;
import org.springframework.stereotype.Service;

@Service
public class CarMapperImpl implements CarMapper{
    @Override
    public CarApiEntity mapCar(com.example.data.db.entity.Car car) {
        return CarApiEntity.builder()
                .carId(car.getCarId())
                .price(car.getPrice())
                .status(car.getStatus())
                .vin(car.getVin())
                .build();
    }
}
