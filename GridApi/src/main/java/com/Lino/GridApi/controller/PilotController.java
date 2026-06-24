package com.Lino.GridApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lino.GridApi.dto.pilot.PilotRequestDTO;
import com.Lino.GridApi.dto.pilot.PilotResponseDTO;
import com.Lino.GridApi.dto.pilot.PilotUpdateRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedRequestDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotComposedResponseDTO;
import com.Lino.GridApi.dto.pilotComposed.PilotUpdateComposedRequestDTO;
import com.Lino.GridApi.service.PilotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pilot")
public class PilotController {

    // Call the PilotService 
    @Autowired
    private PilotService pilotService;

    // POST Simple: Create pilot with nothing attached
    @PostMapping("/simple")
    public ResponseEntity<PilotResponseDTO> saveSimple (@Valid @RequestBody PilotRequestDTO dto) {

        // Call the method that checks the age and save the related
        PilotResponseDTO response = pilotService.simpleSave(dto);

        // Return the data for the front end with the status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //POST Compound: Create pilot with the he license and the circuit links
    @PostMapping("/compound")
    public ResponseEntity<PilotComposedResponseDTO> saveCompound (@Valid @RequestBody PilotComposedRequestDTO dto) {

        // Call the method that checks the age and save the related
        PilotComposedResponseDTO response = pilotService.saveComposed(dto);

        // Return the data for the front end with the status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // GET Dashboard: Full search by ID
    @GetMapping("/dashboard/{id}")
    public ResponseEntity<PilotComposedResponseDTO> getPilotDashboard(@PathVariable Long id) {

        // push the datails informations ready for the front end/Swagger
        PilotComposedResponseDTO response = pilotService.getPilotDashboard(id);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.ok(response);

    }

    // GET Pilots: Get all pilots in the DB
    @GetMapping()
    public ResponseEntity<List<PilotComposedResponseDTO>> getAllPilots () {

        // push the datails informations ready for the front end/Swagger
        List<PilotComposedResponseDTO> responses = pilotService.getAllPilots();

        // Return the list pilots if the status is (OK)
        return ResponseEntity.ok(responses);
        
    }

    // PUT: Uudate a simple pilot
    @PutMapping("/update/simple/{id}")
    public ResponseEntity<PilotResponseDTO> updateSimplePilot(@PathVariable Long id, @Valid @RequestBody PilotUpdateRequestDTO dto) {

        // push the datails informations ready for the front end/Swagger
        PilotResponseDTO response = pilotService.updateSimplePilot(id, dto);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.ok(response);

    }

    // PUT: Uudate a componsed pilot
    @PutMapping("/update/componsed/{id}")
    public ResponseEntity<PilotComposedResponseDTO> updateComposedPilot(@PathVariable Long id, @Valid @RequestBody PilotUpdateComposedRequestDTO dto) {

        // push the datails informations ready for the front end/Swagger
        PilotComposedResponseDTO response = pilotService.updateComposedPilot(id, dto);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.ok(response);

    }
}
