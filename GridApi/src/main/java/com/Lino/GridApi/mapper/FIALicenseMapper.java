package com.Lino.GridApi.mapper;

import org.springframework.stereotype.Component;

import com.Lino.GridApi.dto.FIAlicense.FIALicenseResponseDTO;
import com.Lino.GridApi.dto.FIAlicense.FIAlicenseRequestDTO;
import com.Lino.GridApi.model.FIALicense;

@Component
public class FIALicenseMapper {

    // Converse attributes coming from the front end to the class.
    public static FIALicense toEntity(FIAlicenseRequestDTO dto) {

        // Check if the DTO is not null
        if(dto == null) return null;

        // Create the new license and save in the class all attributes
        FIALicense license = new FIALicense();
        license.setLicenseNumber(dto.licenseNumber());
        license.setCategory(dto.category());
        license.setPenaltyPoints(dto.penaltyPoints());

        // Return the new license
        return license;
    }

    // Transform the pilot into a response to send to the front end. 
    public static FIALicenseResponseDTO toResponseDTO (FIALicense license) {

        // Check if the license is null
        if (license == null) return null;

        // Return the a license ready for the front end
        return new FIALicenseResponseDTO(
            license.getId(),
            license.getLicenseNumber(),
            license.getCategory(),
            license.getPenaltyPoints(),
            license.getPilot() != null ? license.getPilot().getName() : null,
            license.getPilot() != null ? license.getPilot().getAge() : null,
            license.getPilot() != null ? license.getPilot().getCurrentTeam() : null
        );
    }
}
