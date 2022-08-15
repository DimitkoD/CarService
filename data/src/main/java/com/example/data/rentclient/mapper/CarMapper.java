package com.example.data.rentclient.mapper;

import com.example.api.operation2.CarEntity;

public interface CarMapper {
    CarEntity mapCar(com.example.data.db.entity.Car car);
}
