package com.Lino.GridApi.dto.FIAlicense;

import com.Lino.GridApi.dto.pilot.PilotResponseDTO;

public record FIALicenseResponseDTO(
    Long id,
    String licenseNumber,
    String category,
    Integer penaltyPoints,
    PilotResponseDTO pilot
) {

}
