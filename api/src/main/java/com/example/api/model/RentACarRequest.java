package com.example.api.model;

import com.example.api.base.OperationInput;
import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class RentACarRequest implements OperationInput {
    private String carVin;
    private String cardNumber;
    private Integer days;
    private Long customerId;
    private Long employeeId;
}
