package com.example.api.model;

import com.example.api.base.OperationInput;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnCarRequest implements OperationInput {
    private Long carId;
}
