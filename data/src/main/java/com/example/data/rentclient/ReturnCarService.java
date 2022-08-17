package com.example.data.rentclient;

import com.example.api.model.ReturnCarRequest;
import com.example.api.model.ReturnCarResponse;

public interface ReturnCarService {
    ReturnCarResponse returnCar(ReturnCarRequest returnCarRequest);
}
