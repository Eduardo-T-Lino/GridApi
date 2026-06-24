package com.Lino.GridApi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Inform the DB that this is an entitysingle
@AllArgsConstructor // Create the constructors
@NoArgsConstructor // Create a empty constructor
@Getter // Create the Getters
@Setter // Create the Setters
public class FIALicense {

    @Id
    // Make the attribute unique and generated the identity automatically
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseNumber;

    private String category;

    private Integer penaltyPoints;

    // It relates the pilot table to the license, 
    // saying that it's a one-to-one relationship.
    @OneToOne(mappedBy = "fiaLicense")
    private Pilot pilot;
}
