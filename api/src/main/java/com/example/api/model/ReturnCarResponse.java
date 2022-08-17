package com.example.api.model;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReturnCarResponse implements OperationResult {
    private String output;
}
