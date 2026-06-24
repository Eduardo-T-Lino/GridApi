package com.Lino.GridApi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Pilot {

    @Id
    // Make the attribute unique and generated the identity automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String CurrentTeam;

    // It relates the pilot table to the license,
    // saying that it's a one-to-one relationship.
    // And it indicates the name of the table.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fia_license_id", referencedColumnName = "id")
    private FIALicense fiaLicense;

    // It relates the pilot table to the circuit,
    // saying that it's a many-to-many relationship.
    // And it indicates the name of the table.
    @ManyToMany
    @JoinTable(
        name = "pilot_circuit", 
        joinColumns = @JoinColumn(name = "pilot_id"),
        inverseJoinColumns = @JoinColumn(name = "circuit_id")
    )
    private List<Circuit> circuits = new ArrayList<>();

}
