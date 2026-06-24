package com.Lino.GridApi.dto;

import java.util.List;

public record PilotRequestDTO(
    String name,
    Integer age,
    String currentTeam,
    FIAlicenseRequestDTO fiaLicense,
    List<Long> circuitIds
) {

}
