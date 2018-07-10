package com.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacionamiento.models.Vehiculos;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, String>{

}
