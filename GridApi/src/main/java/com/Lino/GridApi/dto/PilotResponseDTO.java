package com.Lino.GridApi.dto;

import java.util.List;

public record PilotResponseDTO(
    Long id,
    String name,
    Integer age,
    String currentTeam,
    String licenseNumber,
    String licenseCategory,
    List<CircuitResponseDTO> circuits
) {

}
