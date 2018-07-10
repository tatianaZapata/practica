package com.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacionamiento.models.TipoVehiculos;

@Repository
public interface TipoVehiculosRepository extends JpaRepository<TipoVehiculos, Integer>{

}
