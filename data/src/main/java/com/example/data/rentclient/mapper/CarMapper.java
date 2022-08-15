package com.example.data.rentclient.mapper;

import com.example.api.operationApi.CarApiEntity;

public interface CarMapper {
    CarApiEntity mapCar(com.example.data.db.entity.Car car);
}
