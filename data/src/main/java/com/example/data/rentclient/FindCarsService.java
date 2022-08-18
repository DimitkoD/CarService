package com.example.data.rentclient;

import com.example.api.model.GetCarsResponse;

public interface FindCarsService {
    GetCarsResponse getCars(Boolean status);
}
