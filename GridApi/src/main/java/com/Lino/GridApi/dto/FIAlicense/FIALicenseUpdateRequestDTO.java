package com.Lino.GridApi.dto.FIAlicense;

public record FIALicenseUpdateRequestDTO(
    String licenseNumber,
    String category,
    Integer penaltyPoints,
    Long pilotId
) {}