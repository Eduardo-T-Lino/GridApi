package com.Lino.GridApi.dto.pilotComposed;

import java.util.List;

import com.Lino.GridApi.dto.FIAlicense.FIAlicenseRequestDTO;

import jakarta.validation.constraints.Min;

public record PilotUpdateComposedRequestDTO(

    String name,

    @Min(value = 18, message = "This child is under 18 years old! Tell him to go play video and get off the real track.")
    Integer age,

    String currentTeam,

    FIAlicenseRequestDTO fiaLicense,

    List<Long> circuitIds
) {

}
