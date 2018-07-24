package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.HistoricoIngresos;

public interface HistoricoIngresosService {
	
	HistoricoIngresos guardarHistorico(HistoricoIngresos historico);
	
	Optional<HistoricoIngresos> consultarHistorico(Integer id);
	
	HistoricoIngresos modificarHistorico(HistoricoIngresos historico);
	
	void eliminarHistorico(Integer id);
	
	List<HistoricoIngresos> listarHistorico ();

}
