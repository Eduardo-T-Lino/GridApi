package com.Lino.GridApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lino.GridApi.dto.PilotRequestDTO;
import com.Lino.GridApi.dto.PilotResponseDTO;
import com.Lino.GridApi.service.PilotService;

@RestController
@RequestMapping("/api/pilot")
public class PilotController {

    @Autowired
    // Call the PilotService 
    private PilotService pilotService;

    //POST Compound: Create pilot with the he license and the circuit links
    @PostMapping("/compound")
    public ResponseEntity<PilotResponseDTO> saveCompound (@RequestBody PilotRequestDTO dto) {

        // Call the method that checks the age and save the related
        PilotResponseDTO response = pilotService.saveComposed(dto);

        // Return the data for the front end with the status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET Dashboard: Full search by ID
    @GetMapping("/dashboard/{id}")
    public ResponseEntity<PilotResponseDTO> getPilotDashboard(@PathVariable Long id) {

        // push the datails informations ready for the front end/Swagger
        PilotResponseDTO response = pilotService.getPilotDashboard(id);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.ok(response);
    }
}
