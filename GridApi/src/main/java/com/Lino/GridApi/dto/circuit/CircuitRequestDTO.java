package com.Lino.GridApi.dto.circuit;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CircuitRequestDTO(

    @NotBlank(message = "The name is mandatory!")
    String name,

    @NotBlank(message = "The country is mandatory!")
    String country,

    @NotNull(message = "The age is mandatory!")
    @Min(value = 3500 , message = "The circuit must have a 3500m long ")
    Integer lengthMeters,

    List<Long> pilotsId
    
) {

}
