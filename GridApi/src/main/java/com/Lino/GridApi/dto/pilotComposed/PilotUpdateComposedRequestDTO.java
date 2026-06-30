package com.Lino.GridApi.dto.pilotComposed;

import java.util.List;

import jakarta.validation.constraints.Min;

public record PilotUpdateComposedRequestDTO(

    String name,

    @Min(value = 18, message = "This child is under 18 years old! Tell him to go play video and get off the real track.")
    Integer age,

    String currentTeam,

    PilotFIALicenseRequestDTO fiaLicense,

    List<Long> circuitIds
) {

}
