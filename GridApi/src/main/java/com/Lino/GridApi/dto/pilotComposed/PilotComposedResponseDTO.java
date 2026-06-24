package com.Lino.GridApi.dto.pilotComposed;

import java.util.List;

public record PilotComposedResponseDTO(
    Long id,
    String name,
    Integer age,
    String currentTeam,
    String licenseNumber,
    String licenseCategory,
    Integer penaltyPoints,
    List<CircuitResponseDTO> circuits
) {

}
