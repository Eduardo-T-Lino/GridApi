package com.Lino.GridApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lino.GridApi.dto.pilot.PilotRequestDTO;
import com.Lino.GridApi.dto.pilot.PilotResponseDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedResponseDTO;
import com.Lino.GridApi.mapper.PilotMapper;
import com.Lino.GridApi.model.Circuit;
import com.Lino.GridApi.model.Pilot;
import com.Lino.GridApi.repository.CircuitRepository;
import com.Lino.GridApi.repository.FIALicenseRepository;
import com.Lino.GridApi.repository.PilotRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PilotService {

    // Inhibit the creation of contructor methods
    @Autowired
    private PilotRepository pilotRepository;

    // Inhibit the creation of contructor methods
    @Autowired
    private FIALicenseRepository licenseRepository;

    // Inhibit the creation of contructor methods
    @Autowired
    private CircuitRepository circuitRepository;

    @Transactional // Perform a rollback in case of an error
    public PilotResponseDTO simpleSave (PilotRequestDTO dto) { // Make the save of the pilot in the DB

        // Check if the name of the pilot already exist in the DB
        if (pilotRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("The pilot with name: " + dto.name() + " is already registered in the grid! ");
        }

        // Converse attributes coming from the front end to the class.
        Pilot pilot = PilotMapper.toSimpleEntity(dto);

        // Save the pilot in the DB
        pilotRepository.save(pilot);

        // Return a simple pilot to the front end
        return PilotMapper.toSimpleResponseDTO(pilot);
        
    }

    @Transactional // Perform a rollback in case of an error
    public PilotComposedResponseDTO saveComposed(PilotComposedRequestDTO dto) { // Make the save of the pilot and license together.

        // Check if the name of the pilot already exist in the DB
        if (pilotRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("The pilot with name: " + dto.name() + " is already registered in the grid! ");
        }

        // Check if the license is not null
        if (dto.fiaLicense() != null) {
            // Check if the license already exist in the DB
            boolean licenseExist = licenseRepository.existsByLicenseNumber(dto.fiaLicense().licenseNumber());
            if (licenseExist) {
                throw new IllegalArgumentException("The FIA license " + dto.fiaLicense().licenseNumber() + " is already in use by another pilot! ");
            }
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

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public PilotComposedResponseDTO getPilotDashboard(Long id) { // Return the dashboard with all informations of the pilot.

        // Found the pilot in the DB.
        // With the pilot don't exist, send an error for the user
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot with the ID: " + id + " don't exist in the DB"));

        return PilotMapper.toResponseDTO(pilot);
    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public List<PilotComposedResponseDTO> getAllPilots () { // Return all the pilots existing in the DB

        // Take all pilots existing in the DB
        List<Pilot> pilots = pilotRepository.findAll();

        // Check if exist some pilot in the DB
        if (pilots.size() <= 0 || pilots == null) {
            throw new RuntimeException("No have any pilot registered in the grid! ");
        }

        // Return the list pilots.
        return pilots.stream().map(PilotMapper :: toResponseDTO).toList();
        
    }

}