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

import com.Lino.GridApi.dto.FIAlicense.FIALicenseResponseDTO;
import com.Lino.GridApi.dto.FIAlicense.FIALicenseUpdateRequestDTO;
import com.Lino.GridApi.dto.FIAlicense.FIAlicenseRequestDTO;
import com.Lino.GridApi.service.FIALicenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/license")
public class FIALicenseController {

    // Call the licenseService 
    @Autowired
    private FIALicenseService licenseService;

    // POST Simple: Create license with nothing attached
    @PostMapping("/post")
    public ResponseEntity<FIALicenseResponseDTO> saveSimple (@Valid @RequestBody FIAlicenseRequestDTO dto) {

        // Call the method and save the related
        FIALicenseResponseDTO response = licenseService.saveLicense(dto);

        // Return the data for the front end with the status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // GET Dashboard: Full search by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<FIALicenseResponseDTO> getCircuit(@PathVariable Long id) {

        // push the datails informations ready for the front end/Swagger
        FIALicenseResponseDTO response = licenseService.getLicenseById(id);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // GET Pilots: Get all license in the DB
    @GetMapping()
    public ResponseEntity<List<FIALicenseResponseDTO>> getAllCircuits () {

        // push the datails informations ready for the front end/Swagger
        List<FIALicenseResponseDTO> responses = licenseService.getAllLicenses();

        // Return the list pilots if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(responses);
        
    }

    // PUT: Update a license
    @PutMapping("/update/{id}")
    public ResponseEntity<FIALicenseResponseDTO> updateCircuit(@PathVariable Long id, @Valid @RequestBody FIALicenseUpdateRequestDTO dto) {

        // push the datails informations ready for the front end/Swagger
        FIALicenseResponseDTO response = licenseService.updateLicense(id, dto);

        // Return the dashboard if the status is (OK)
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // DELETE: Delete license
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCircuit (@PathVariable Long id) {

        // Delete a circuit
        licenseService.deleteLicense(id);

        // Return the status of the Query
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

}