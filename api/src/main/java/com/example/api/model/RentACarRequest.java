package com.example.api.model;

import com.example.api.base.OperationInput;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RentACarRequest implements OperationInput {
    @Length(min = 17, max = 17)
    private String carVin;
    @NotBlank
    private String cardNumber;
    @Min(value = 1, message = "Days must be 1 or more")
    private Integer days;
    @Min(1)
    private Long customerId;
    @Min(1)
    private Long employeeId;
}
