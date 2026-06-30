package com.Lino.GridApi.dto.circuit;

import java.util.List;

import jakarta.validation.constraints.Min;

public record CircuitUpdateRequestDTO(

    String name,

    String country,

    @Min(value = 3500 , message = "The circuit must have a 3500m long ")
    Integer lengthMeters,

    List<Long> pilotsId
) {

}
