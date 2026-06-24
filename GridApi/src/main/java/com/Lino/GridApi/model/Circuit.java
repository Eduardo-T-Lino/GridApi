package com.Lino.GridApi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Inform the DB that this is an entitysingle
@AllArgsConstructor // Create the constructors
@NoArgsConstructor // Create a empty constructor
@Getter // Create the Getters
@Setter // Create the Setters
public class Circuit {

    @Id
    // Make the attribute unique and generated the identity automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private Integer lengthMeters;

    // It relates the pilot table to the circuit, 
    // saying that it's a many-to-many relationship.
    @ManyToMany(mappedBy = "circuits")
    private List<Pilot> pilots = new ArrayList<>();

}
