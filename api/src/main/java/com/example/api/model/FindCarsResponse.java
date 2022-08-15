package com.example.api.model;

import com.example.api.base.OperationResult;
import com.example.api.operation2.CarPOJO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class FindCarsResponse implements OperationResult {
    List<CarPOJO> carsAvailable;
}
