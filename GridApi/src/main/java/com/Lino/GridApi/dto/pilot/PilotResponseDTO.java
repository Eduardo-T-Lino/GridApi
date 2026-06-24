package com.Lino.GridApi.dto.pilot;

public record PilotResponseDTO(
    Long id,
    String name,
    Integer age,
    String currentTeam
) {

}
