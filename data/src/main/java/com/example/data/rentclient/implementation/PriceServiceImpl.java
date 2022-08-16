package com.example.data.rentclient.implementation;

import com.example.data.rentclient.PriceService;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

    public Double getPrice(int days, double price) {
        return days * price;
    }

}
