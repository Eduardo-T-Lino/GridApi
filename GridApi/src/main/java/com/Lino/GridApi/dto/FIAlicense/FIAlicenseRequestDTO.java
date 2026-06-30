package com.Lino.GridApi.dto.FIAlicense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FIAlicenseRequestDTO(

    @NotBlank(message = "The license number is mandatory!")
    String licenseNumber,

    @NotBlank(message = "The category is mandatory!")
    String category,

    @NotNull(message = "The penalty points is mandatory!")
    Integer penaltyPoints,

    Long pilotId
    
) {

}
