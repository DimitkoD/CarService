package com.example.api.model;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class RentACarResponse implements OperationResult {
    private String output;
}
