package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.HistoricoIngresos;

public interface HistoricoIngresosService {
	
	HistoricoIngresos guardarHistorico(HistoricoIngresos historico) throws Exception;
	
	Optional<HistoricoIngresos> consultarHistorico(Integer id) throws Exception;
	
	HistoricoIngresos modificarHistorico(HistoricoIngresos historico) throws Exception;
	
	void eliminarHistorico(Integer id) throws Exception;
	
	List<HistoricoIngresos> listarHistorico () throws Exception;

}
