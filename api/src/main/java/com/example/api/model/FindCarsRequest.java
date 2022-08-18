package com.example.api.model;


import com.example.api.base.OperationInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FindCarsRequest implements OperationInput {
    @NotBlank
    private Boolean status;
}
