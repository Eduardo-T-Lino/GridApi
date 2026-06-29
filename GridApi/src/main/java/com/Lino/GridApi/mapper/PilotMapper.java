package com.Lino.GridApi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.Lino.GridApi.dto.pilot.PilotRequestDTO;
import com.Lino.GridApi.dto.pilot.PilotResponseDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotCircuitResponseDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedResponseDTO;
import com.Lino.GridApi.model.FIALicense;
import com.Lino.GridApi.model.Pilot;

@Component
public class PilotMapper {

    // Converse attributes coming from the front end to the class.
    public static Pilot toEntity(PilotComposedRequestDTO dto) {
        
        // Check if the informations coming from the front end is not null
        if (dto == null) return null;

        // Create a new pilot
        Pilot pilot = new Pilot();
        // Make the pilot store the data
        pilot.setName(dto.name());
        pilot.setAge(dto.age());
        pilot.setCurrentTeam(dto.currentTeam());

        // Check if the fiaLicense is not null
        if (dto.fiaLicense() != null) {
            // Create a new License
            FIALicense license = new FIALicense();
            // Make the license store the data
            license.setLicenseNumber(dto.fiaLicense().licenseNumber());
            license.setCategory(dto.fiaLicense().category());
            license.setPenaltyPoints(dto.fiaLicense().penaltyPoints());

            // Save the license in the pilot
            pilot.setFiaLicense(license);

            // Save the pilot in the license
            license.setPilot(pilot);

        }

        return pilot;
    }

    // Transform the pilot into a response to send to the front end.
    public static PilotComposedResponseDTO toResponseDTO (Pilot pilot) {

        // Check if the pilot is not null.
        if (pilot == null) return null;

        // Create a list circuits to save in the pilot.
        List<PilotCircuitResponseDTO> circuits = new ArrayList<>();

        // Check if the pilot has circuits.
        if (pilot.getCircuits() != null) {
            // Save all circuits in the list with all attributes
            circuits = pilot.getCircuits().stream().map(c -> new PilotCircuitResponseDTO(c.getId(),c.getName(), c.getCountry(), c.getLengthMeters()))
            .toList();
        }

        // Return the pilot with all informations and attributes
        return new PilotComposedResponseDTO(
            pilot.getId(),
            pilot.getName(),
            pilot.getAge(),
            pilot.getCurrentTeam(),
            pilot.getFiaLicense() != null ? pilot.getFiaLicense().getLicenseNumber() : null,
            pilot.getFiaLicense() != null ? pilot.getFiaLicense().getCategory() : null,
            pilot.getFiaLicense() != null ? pilot.getFiaLicense().getPenaltyPoints() : null,
            circuits
        );
    }

    // Converse attributes coming from the front end to the class.
    public static Pilot toSimpleEntity (PilotRequestDTO dto) {

        // Create a new pilot
        Pilot pilot = new Pilot();
        // Make the pilot store the data
        pilot.setName(dto.name());
        pilot.setAge(dto.age());
        pilot.setCurrentTeam(dto.currentTeam());
        pilot.setFiaLicense(null);

        // Return the Pilot.
        return pilot;

    }

    // Transform the pilot into a response to send to the front end. 
    public static PilotResponseDTO toSimpleResponseDTO (Pilot pilot) {

        // Check if the pilot is not null.
        if (pilot == null) return null;

        // Return the pilot to send then to the front end
        return new PilotResponseDTO(
            pilot.getId(),
            pilot.getName(),
            pilot.getAge(),
            pilot.getCurrentTeam()
        );
    }
}
