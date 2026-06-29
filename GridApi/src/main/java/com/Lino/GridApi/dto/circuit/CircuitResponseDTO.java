package com.Lino.GridApi.dto.circuit;

import java.util.List;

import com.Lino.GridApi.dto.pilot.PilotResponseDTO;

public record CircuitResponseDTO(
    Long id,
    String name,
    String country,
    Integer lengthMeters,
    List<PilotResponseDTO> pilots
) {

}
