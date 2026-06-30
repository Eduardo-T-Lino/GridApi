package com.Lino.GridApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Lino.GridApi.dto.FIAlicense.FIALicenseResponseDTO;
import com.Lino.GridApi.dto.FIAlicense.FIAlicenseRequestDTO;
import com.Lino.GridApi.mapper.FIALicenseMapper;
import com.Lino.GridApi.model.FIALicense;
import com.Lino.GridApi.model.Pilot;
import com.Lino.GridApi.repository.FIALicenseRepository;
import com.Lino.GridApi.repository.PilotRepository;

@Service
public class FIALicenseService {

    @Autowired // Inhibit the creation of contructor methods
    private FIALicenseRepository licenseRepository;

    @Autowired // Inhibit the creation of contructor methods
    private PilotRepository pilotRepository;

    @Transactional // Perform a rollback in case of an error
    public FIALicenseResponseDTO saveLicense (FIAlicenseRequestDTO dto) { // Create and save a new License in the DB

        // Check if the FIA license already existing in the DB
        if (licenseRepository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new IllegalArgumentException("The FIA license with number: " + dto.licenseNumber() + " Already existing in the grid! ");
        }

        // Transform the informations coming to the Front end for the class 
        FIALicense license = FIALicenseMapper.toEntity(dto);

        // Take the pilot in the DB or throw an error
        Pilot pilot = pilotRepository.findById(dto.pilotId())
            .orElseThrow(() -> new RuntimeException("Pilot with the ID: " + dto.pilotId() + " don't exist in the grid!"));

        // Ckeck if the pilot already has some license or throw an error
        if (pilot.getFiaLicense() == null) {
            
            license.setPilot(pilot);

            pilot.setFiaLicense(license);

        }
        else {
            throw new IllegalArgumentException("The pilot with name: " + pilot.getName() + " Already has a FIA license.");
        }

        // Save the new license in the DB
        FIALicense savedLicense = licenseRepository.save(license);

        // Return the new license
        return FIALicenseMapper.toResponseDTO(savedLicense);

    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public FIALicenseResponseDTO getLicenseById (Long id) { // Get the License by id

        // Take the license via DB
        FIALicense license = licenseRepository.findById(id)
            .orElseThrow( () -> new RuntimeException("The FIA License with id: " + id + " dont't exist in the grid! "));

        // Return the license to the Front end
        return FIALicenseMapper.toResponseDTO(license);

    }

    @Transactional(readOnly = true) // Perform a rollback in case of an error
    public List<FIALicenseResponseDTO> getAllLicenses () { // Get all license existing in the DB

        // Take all licenses via DB
        List<FIALicense> licenses = licenseRepository.findAll();

        // Return all licenses ready for the Front
        return licenses.stream()
        .map(FIALicenseMapper :: toResponseDTO)
        .toList();
        
    }
}