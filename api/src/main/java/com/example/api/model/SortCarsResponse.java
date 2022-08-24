package com.example.api.model;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SortCarsResponse implements OperationResult {
    Map<String, Integer> sortedCarsByTimesRented;
}
