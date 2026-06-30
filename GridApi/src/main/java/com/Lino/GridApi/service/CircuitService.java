package com.Lino.GridApi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Lino.GridApi.dto.circuit.CircuitRequestDTO;
import com.Lino.GridApi.dto.circuit.CircuitResponseDTO;
import com.Lino.GridApi.dto.circuit.CircuitUpdateRequestDTO;
import com.Lino.GridApi.mapper.CircuitMapper;
import com.Lino.GridApi.model.Circuit;
import com.Lino.GridApi.model.Pilot;
import com.Lino.GridApi.repository.CircuitRepository;
import com.Lino.GridApi.repository.PilotRepository;

@Service
public class CircuitService {

    @Autowired // Inhibit the creation of contructor methods
    private CircuitRepository circuitRepository;

    @Autowired // Inhibit the creation of contructor methods
    private PilotRepository pilotRepository;

    @Transactional // Perform a rollback in case of an error
    public CircuitResponseDTO saveCircuit (CircuitRequestDTO dto) { // Save the circuit in the DB

        // Chech if the name of the circuit already exist in the DB
        if (circuitRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException(
                    "The circuit with name: " + dto.name() + " is already registered in the grid! ");
        }

        // Converse attributes coming from the front end to the class
        Circuit circuit = CircuitMapper.toEntity(dto);

        // Check if the pilots identify list is not null
        if (dto.pilotsId() != null) {
            
            // Create a list for the pilots
            List<Pilot> pilots = new ArrayList<>();

            // Pass for all pilots id
            for (Long id : dto.pilotsId()) {

                // Take the pilot in the DB
                Pilot pilot = pilotRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("The pilot with ID: " + id + " no exist in the DB"));

                // Check if the circuit already exist in the pilot
                if (!pilot.getCircuits().contains(circuit)) {
                    // Save the circuit in the pilot
                    pilot.getCircuits().add(circuit);
                }

                // Check if the pilot already exist in the circuit
                if (!circuit.getPilots().contains(pilot)) {
                    // Save the pilot in the circuit
                    circuit.getPilots().add(pilot);
                }

            }

            // Save the pilot in the circuit
            circuit.setPilots(pilots);

        }

        // Save the circuit in the DB
        Circuit circuitSaved = circuitRepository.save(circuit);

        // Return a circuit to the front end
        return CircuitMapper.toResponseDTO(circuitSaved);

    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public CircuitResponseDTO getCircuit (Long id) { // Return the Circuit reference the insert id

        // Found the circuit in the DB
        Circuit circuit = circuitRepository.findById(id)
            .orElseThrow( () -> new RuntimeException("The circuit with ID: " + id + " no exist in the DB"));

        // Return the circuit found
        return CircuitMapper.toResponseDTO(circuit);

    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public List<CircuitResponseDTO> getAllCircuits () { // Return all circuits existing in the DB

        // Take all circuits in the DB
        List<Circuit> circuits = circuitRepository.findAll();

        if (circuits.size() <= 0 || circuits == null) {
            throw new RuntimeException("No have any circuit registered in the grid! ");
        }
    
        // Return all circuits for the front 
        return circuits.stream().map(CircuitMapper :: toResponseDTO).toList();
        
    }

    @Transactional // Perform a rollback in case of an error
    public CircuitResponseDTO updateCircuit (Long id, CircuitUpdateRequestDTO dto) { // Update the informations of 1 circuit

        // Take the circuit in the DB
        Circuit circuit = circuitRepository.findById(id)
            .orElseThrow( () -> new RuntimeException("The circuit with ID: " + id + " no exist in the DB"));

        // Check if the circuit name is not null
        if (dto.name() != null) {

            // Check if the name is not blank
            if (!dto.name().isBlank()) {
                
                // Check if the name exist in the DB or equals
                if (!dto.name().equalsIgnoreCase(circuit.getName()) && circuitRepository.existsByName(dto.name())) {

                    // Launch the exception to the front end.
                    throw new IllegalArgumentException(
                            "The pilot with name: " + dto.name() + " is already registered in the grid! ");

                }
                // Save the new name
                circuit.setName(dto.name());
            }
            
        }

        // Check if the country is not null
        if (dto.country() != null ) {

            // Check if the country is not black
            if (!dto.country().isBlank()) {
                // Save the new Country
                circuit.setCountry(dto.country());
            }
        }

        // Check if the length meters is not null
        if (dto.lengthMeters() != null) {
            circuit.setLengthMeters(dto.lengthMeters());
        }

        // Check if the pilots id is not null
        if (dto.pilotsId() != null) {
            
            // Pass for all pilots identify
            for (Long pilotId : dto.pilotsId()) {
                
                // Take the pilot in the DB
                Pilot pilot = pilotRepository.findById(pilotId)
                .orElseThrow(() -> new RuntimeException("Pilot with the ID: " + id + " don't exist in the DB"));

                // Check if the circuit already exist in the pilot
                if (!pilot.getCircuits().contains(circuit)) {
                    // Save the circuit in the pilot
                    pilot.getCircuits().add(circuit);
                }

                // Check if the pilot already exist in the circuit
                if (!circuit.getPilots().contains(pilot)) {
                    // Save the pilot in the circuit
                    circuit.getPilots().add(pilot);
                }

            }
        }

        // Return the circuit with the new attributes for the front end
        return CircuitMapper.toResponseDTO(circuit);

    }

    @Transactional // Perform a rollback in case of an error
    public void deletePilot(Long id) { // Delete the circuit

        // Find the circuit with the user need
        Circuit circuit = circuitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("The circuit with ID: " + id + " no exist in the DB"));

        // Delete the circuit, regardless of whether they have a license.
        circuitRepository.delete(circuit);
        
    }
}
