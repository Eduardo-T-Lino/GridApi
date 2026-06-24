package com.Lino.GridApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Lino.GridApi.model.Circuit;

// Repository annotation for using the methods:
// save, findById, findAll and Delete
@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long>{

}
