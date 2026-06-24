package com.Lino.GridApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lino.GridApi.dto.pilot.PilotRequestDTO;
import com.Lino.GridApi.dto.pilot.PilotResponseDTO;
import com.Lino.GridApi.dto.pilot.PilotUpdateRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedResponseDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotUpdateComposedRequestDTO;
import com.Lino.GridApi.mapper.PilotMapper;
import com.Lino.GridApi.model.Circuit;
import com.Lino.GridApi.model.FIALicense;
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
    public PilotResponseDTO simpleSave(PilotRequestDTO dto) { // Make the save of the pilot in the DB

        // Check if the name of the pilot already exist in the DB
        if (pilotRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException(
                    "The pilot with name: " + dto.name() + " is already registered in the grid! ");
        }

        // Converse attributes coming from the front end to the class.
        Pilot pilot = PilotMapper.toSimpleEntity(dto);

        // Save the pilot in the DB
        pilotRepository.save(pilot);

        // Return a simple pilot to the front end
        return PilotMapper.toSimpleResponseDTO(pilot);

    }

    @Transactional // Perform a rollback in case of an error
    public PilotComposedResponseDTO saveComposed(PilotComposedRequestDTO dto) { // Make the save of the pilot and
                                                                                // license together.

        // Check if the name of the pilot already exist in the DB
        if (pilotRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException(
                    "The pilot with name: " + dto.name() + " is already registered in the grid! ");
        }

        // Check if the license is not null
        if (dto.fiaLicense() != null) {
            // Check if the license already exist in the DB
            boolean licenseExist = licenseRepository.existsByLicenseNumber(dto.fiaLicense().licenseNumber());
            if (licenseExist) {
                throw new IllegalArgumentException("The FIA license " + dto.fiaLicense().licenseNumber()
                        + " is already in use by another pilot! ");
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
    public PilotComposedResponseDTO getPilotDashboard(Long id) { // Return the dashboard with all informations of the
                                                                 // pilot.

        // Found the pilot in the DB.
        // With the pilot don't exist, send an error for the user
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot with the ID: " + id + " don't exist in the DB"));

        return PilotMapper.toResponseDTO(pilot);
    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public List<PilotComposedResponseDTO> getAllPilots() { // Return all the pilots existing in the DB

        // Take all pilots existing in the DB
        List<Pilot> pilots = pilotRepository.findAll();

        // Check if exist some pilot in the DB
        if (pilots.size() <= 0 || pilots == null) {
            throw new RuntimeException("No have any pilot registered in the grid! ");
        }

        // Return the list pilots.
        return pilots.stream().map(PilotMapper::toResponseDTO).toList();

    }

    @Transactional // Perform a rollback in case of an error
    public PilotResponseDTO updateSimplePilot(Long id, PilotUpdateRequestDTO dto) { // Update the pilot alter his name, age and current team

        // Take the pilot by his id
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The pilot with ID: " + id + " no exist in the DB"));

        // Check if the name is not null and if the name already exist in the grid
        if (dto.name() != null) {
            if (!dto.name().isBlank()) {

                // Check if the current name is diferent of the name save in the grid
                if (!dto.name().equalsIgnoreCase(pilot.getName()) && pilotRepository.existsByName(dto.name())) {
                    // Launch the exception to the front end.
                    throw new IllegalArgumentException(
                            "The pilot with name: " + dto.name() + " is already registered in the grid! ");
                }

                pilot.setName(dto.name());

            }
        }

        // Check if the age is not null and save in the pilot
        if (dto.age() != null) {

            pilot.setAge(dto.age());

        }

        // Check if the currentTeam is not null and save in the pilot
        if (dto.currentTeam() != null) {

            pilot.setCurrentTeam(dto.currentTeam());

        }

        // Return the new pilot for the front end
        return PilotMapper.toSimpleResponseDTO(pilot);

    }

    @Transactional // Perform a rollback in case of an error
    public PilotComposedResponseDTO updateComposedPilot(Long id, PilotUpdateComposedRequestDTO dto) {

        // Take the pilot by his id
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The pilot with ID: " + id + " no exist in the DB"));

        // Check if the new name is not null
        if (dto.name() != null) {

            // Check if the new name is not black
            if (!dto.name().isBlank()) {

                // Check if the current name is diferent of the name save in the grid
                if (!dto.name().equalsIgnoreCase(pilot.getName()) && pilotRepository.existsByName(dto.name())) {
                    // Launch the exception to the front end.
                    throw new IllegalArgumentException(
                            "The pilot with name: " + dto.name() + " is already registered in the grid! ");
                }
                pilot.setName(dto.name());
            }
        }

        // Check if the age is not null and save in the pilot
        if (dto.age() != null) {
            pilot.setAge(dto.age());
        }

        // Check if the currentTeam is not null and save in the pilot
        if (dto.currentTeam() != null) {
            pilot.setCurrentTeam(dto.currentTeam());
        }

        // Check if the FIA license is not null
        if (dto.fiaLicense() != null) {

            // Take the license in the data base
            FIALicense currentLicense = pilot.getFiaLicense();

            // Check if the pilot don't any license
            if (currentLicense == null) {
                currentLicense = new FIALicense();
                currentLicense.setPilot(pilot);
                pilot.setFiaLicense(currentLicense);
            }

            // Check if the user is try to update the license number
            if (dto.fiaLicense().licenseNumber() != null && !dto.fiaLicense().licenseNumber().isBlank()) {

                // Check if the number is diferent of the current number
                if (!dto.fiaLicense().licenseNumber().equalsIgnoreCase(currentLicense.getLicenseNumber())) {

                    // Check if the new number is already registered in another pilot.
                    if (licenseRepository.existsByLicenseNumber(dto.fiaLicense().licenseNumber())) {
                        throw new IllegalArgumentException("The FIA license " + dto.fiaLicense().licenseNumber()
                                + " is already in use by another pilot!");
                    }
                }
                currentLicense.setLicenseNumber(dto.fiaLicense().licenseNumber());
            }
            // Check if the license category change
            if (dto.fiaLicense().category() != null && !dto.fiaLicense().category().isBlank()) {
                currentLicense.setCategory(dto.fiaLicense().category());
            }

            // Check if the license points change
            if (dto.fiaLicense().penaltyPoints() != null) {
                currentLicense.setPenaltyPoints(dto.fiaLicense().penaltyPoints());
            }
        }
    
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

        // Return the pilot composed
        return PilotMapper.toResponseDTO(pilot);

    }
}