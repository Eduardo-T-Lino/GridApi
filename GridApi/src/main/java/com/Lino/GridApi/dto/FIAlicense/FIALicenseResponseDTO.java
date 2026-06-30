package com.Lino.GridApi.dto.FIAlicense;

public record FIALicenseResponseDTO(
    Long id,
    String licenseNumber,
    String category,
    Integer penaltyPoints,
    String pilotName,
    Integer pilotAge,
    String pilotTeam
) {

}
