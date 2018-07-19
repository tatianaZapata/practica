package com.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estacionamiento.models.HistoricoIngresos;

@Repository
public interface HistoricoIngresosRepository extends JpaRepository<HistoricoIngresos, Integer>{
	
	Optional<HistoricoIngresos> findTop1ByPlacaOrderByFechaIngresoDesc(@Param("placa") String placa);

}
