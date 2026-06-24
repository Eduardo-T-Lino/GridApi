package com.Lino.GridApi.dto;

public record FIAlicenseRequestDTO(
    String licenseNumber,
    String category,
    Integer penaltyPoints
) {

}
