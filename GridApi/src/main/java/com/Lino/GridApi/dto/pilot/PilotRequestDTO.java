package com.Lino.GridApi.dto.pilot;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PilotRequestDTO(

    @NotBlank(message = "The name is mandatory!")
    String name,

    @NotNull(message = "The age is mandatory!")
    @Min(value = 18, message = "This child is under 18 years old! Tell him to go play video and get off the real track.")
    Integer age,

    @NotBlank(message = "The current team is mandatory!")
    String currentTeam
) {

}
