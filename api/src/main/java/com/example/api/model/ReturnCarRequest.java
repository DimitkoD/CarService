package com.example.api.model;

import com.example.api.base.OperationInput;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ReturnCarRequest implements OperationInput {
    private Long carId;
}
