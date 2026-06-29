package com.Lino.GridApi.mapper;

import java.util.ArrayList;
import java.util.List;

import com.Lino.GridApi.dto.circuit.CircuitRequestDTO;
import com.Lino.GridApi.dto.circuit.CircuitResponseDTO;
import com.Lino.GridApi.dto.pilot.PilotResponseDTO;
import com.Lino.GridApi.model.Circuit;

public class CircuitMapper {

    // Converse attributes coming from the front end to the class.
    public static Circuit toEntity (CircuitRequestDTO dto) {

        // Check if the informations coming from the front end is not null
        if (dto == null) return null;

        // Create a new Circuit
        Circuit circuit = new Circuit();
        // Make the circuit store the data
        circuit.setName(dto.name());
        circuit.setCountry(dto.country());
        circuit.setLengthMeters(dto.lengthMeters());

        // Return this new Circuit
        return circuit;

    }

    // Transform the circuit into a response to send to the front end.
    public static CircuitResponseDTO toResponseDTO(Circuit circuit) {

        // Check if the circuit is not null.
        if (circuit == null) return null;

        // Create a list pilots to save in the circuit
        List<PilotResponseDTO> pilots = new ArrayList<>();

        // Check if the circuit has pilots
        if (circuit.getPilots() != null) {
            // Save all pilots in the list with all attributes
            pilots = circuit.getPilots().stream().map(p -> 
                new PilotResponseDTO(p.getId(),p.getName(), p.getAge(), p.getCurrentTeam()))
                .toList();
        }

        // Return the circuit with all informations and attributes
        return new CircuitResponseDTO(
            circuit.getId(),
            circuit.getName(),
            circuit.getCountry(),
            circuit.getLengthMeters(),
            pilots
        );
    }
}
