package com.example.api.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class SortEmployeesResponse {
    Map<String, Integer> sortedEmployeesByTimesRented;
}
