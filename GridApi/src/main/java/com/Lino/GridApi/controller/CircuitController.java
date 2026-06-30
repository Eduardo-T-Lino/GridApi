package com.Lino.GridApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lino.GridApi.dto.circuit.CircuitRequestDTO;
import com.Lino.GridApi.dto.circuit.CircuitResponseDTO;
import com.Lino.GridApi.dto.circuit.CircuitUpdateRequestDTO;
import com.Lino.GridApi.service.CircuitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/circuit")
public class CircuitController {

    // Call the PilotService 
    @Autowired
    private CircuitService circuitService;

    // POST Simple: Create circuit with nothing attached
    @PostMapping("/post")
    public ResponseEntity<CircuitResponseDTO> saveSimple (@Valid @RequestBody CircuitRequestDTO dto) {

        // Call the method and save the related
        CircuitResponseDTO response = circuitService.saveCircuit(dto);

        // Return the data for the front end with the status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // GET Dashboard: Full search by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<CircuitResponseDTO> getCircuit(@PathVariable Long id) {

        // push the datails informations ready for the front end/Swagger
        CircuitResponseDTO response = circuitService.getCircuit(id);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // GET Pilots: Get all circuits in the DB
    @GetMapping()
    public ResponseEntity<List<CircuitResponseDTO>> getAllCircuits () {

        // push the datails informations ready for the front end/Swagger
        List<CircuitResponseDTO> responses = circuitService.getAllCircuits();

        // Return the list pilots if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(responses);
        
    }

    // PUT: Update a circuit
    @PutMapping("/update/{id}")
    public ResponseEntity<CircuitResponseDTO> updateCircuit(@PathVariable Long id, @Valid @RequestBody CircuitUpdateRequestDTO dto) {

        // push the datails informations ready for the front end/Swagger
        CircuitResponseDTO response = circuitService.updateCircuit(id, dto);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // DELETE: Delete circuit
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCircuit (@PathVariable Long id) {

        // Delete a circuit
        circuitService.deletePilot(id);

        // Return the status of the Query
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

}