package com.example.data.rentclient;

import com.example.api.operation2.ApiResponse;

public interface FindCarsService {
    ApiResponse getCars(String status);
}
