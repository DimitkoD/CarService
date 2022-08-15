package com.example.api.model;

import com.example.api.base.OperationInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCarsRequest implements OperationInput {
    private String status;
}
