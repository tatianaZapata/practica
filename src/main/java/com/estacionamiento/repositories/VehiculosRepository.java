package com.estacionamiento.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacionamiento.models.Vehiculos;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, String>{

	List<Vehiculos> findByFechaIngreso(LocalDateTime fechaIngreso);

}
