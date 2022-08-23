package com.example.api.model;

import com.example.api.base.OperationResult;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RentACarResponse implements OperationResult {
    private String output;
}
