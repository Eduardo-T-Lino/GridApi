package com.Lino.GridApi.dto.pilot;

import jakarta.validation.constraints.Min;

public record PilotUpdateRequestDTO(

    String name,

    @Min(value = 18, message = "This child is under 18 years old! Tell him to go play video and get off the real track.")
    Integer age,

    String currentTeam

) {

}
