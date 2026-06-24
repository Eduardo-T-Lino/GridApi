package com.Lino.GridApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lino.GridApi.dto.PilotRequestDTO;
import com.Lino.GridApi.dto.PilotResponseDTO;
import com.Lino.GridApi.mapper.PilotMapper;
import com.Lino.GridApi.model.Circuit;
import com.Lino.GridApi.model.Pilot;
import com.Lino.GridApi.repository.CircuitRepository;
import com.Lino.GridApi.repository.PilotRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PilotService {

    // Inhibit the creation of contructor methods
    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private CircuitRepository circuitRepository;

    @Transactional // Perform a rollback in case of an error
    public PilotResponseDTO saveComposed(PilotRequestDTO dto) { // Make the save of the pilot and circuit together.

        // Check if the age was provided and if the pilot is under 18.
        if (dto.age() == null) {
            throw new IllegalArgumentException("The age field is mandatory!");
        }

        if (dto.age() < 18) {
            throw new IllegalArgumentException(
                    "This child is under 18 years old! Tell him to go play video and get off the real track.");
        }

        // Converse attributes coming from the front end to the class.
        Pilot pilot = PilotMapper.toEntity(dto);

        // Check if the circuit identify is not null or empty.
        if (dto.circuitIds() != null && !dto.circuitIds().isEmpty()) {
            for (Long circuitId : dto.circuitIds()) {

                // Try pick the circuit, if don't found, launch a Error for the user
                Circuit circuit = circuitRepository.findById(circuitId)
                        .orElseThrow(() -> new RuntimeException(
                                "Circuit with ID: " + circuitId + " it wasn't found on the calendar!"));

                // Save the circuit in the pilot
                pilot.getCircuits().add(circuit);

                // Save the pilot in the circuit automatically
                circuit.getPilots().add(pilot);

            }
        }

        // Save the pilot in the DB.
        // The cascade go to save the license automatically
        Pilot savedPilot = pilotRepository.save(pilot);

        // Return the saved pilot.
        return PilotMapper.toResponseDTO(savedPilot);
    }

    // Return the dashboard with all informations of the pilot
    @Transactional(readOnly = true)
    public PilotResponseDTO getPilotDashboard(Long id) {

        // Found the pilot in the DB.
        // With the pilot don't exist, send an error for the user
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot with the ID: " + id + " don't exist in the DB"));

        return PilotMapper.toResponseDTO(pilot);
    }
}
