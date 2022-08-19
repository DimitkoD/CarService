package com.example.data.rentclient;

import com.example.api.model.GetCarsResponse;
import com.example.data.db.entity.Car;

import java.util.List;

public interface FindCarsService {
    GetCarsResponse getCars(List<Car> cars);
}
