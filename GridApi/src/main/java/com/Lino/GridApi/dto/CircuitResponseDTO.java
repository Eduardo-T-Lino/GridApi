package com.Lino.GridApi.dto;

public record CircuitResponseDTO(
    Long id,
    String name,
    String country,
    Integer lengthMetres
) {

}
