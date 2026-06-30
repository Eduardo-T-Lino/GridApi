package com.Lino.GridApi.dto.pilotComposed;

import java.util.List;

import com.Lino.GridApi.dto.FIAlicense.FIAlicenseRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PilotComposedRequestDTO(

    @NotBlank(message = "The name is mandatory!")
    String name,

    @NotNull(message = "The age is mandatory!")
    @Min(value = 18, message = "This child is under 18 years old! Tell him to go play video and get off the real track.")
    Integer age,

    @NotBlank(message = "The current team is mandatory!")
    String currentTeam,

    @NotNull(message = "The FIA license data is mandatory!")
    FIAlicenseRequestDTO fiaLicense,

    List<Long> circuitIds
) {

}
