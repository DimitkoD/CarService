package com.example.data.rentclient;

import com.example.api.operationApi.ApiResponse;

public interface FindCarsService {
    ApiResponse getCars(Boolean status);
}
