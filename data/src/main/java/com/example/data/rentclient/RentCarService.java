package com.example.data.rentclient;

import com.example.api.model.RentACarRequest;
import com.example.api.model.RentACarResponse;

public interface RentCarService {
    RentACarResponse rentACar(RentACarRequest rentACarRequest);
}
