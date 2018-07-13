package com.estacionamiento.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento.models.HistoricoIngresos;
import com.estacionamiento.services.HistoricoIngresosService;

@RestController
@RequestMapping("/api")
public class HistoricoIngresosController {
	
	@Autowired
	private HistoricoIngresosService historicoIngresosService;
	
	@GetMapping("/historico/{id}")
	public Optional<HistoricoIngresos> consultarHistorico(@PathVariable Integer id) throws Exception{
		return historicoIngresosService.consultarHistorico(id);
	}
	
	@GetMapping("/historico")
	public List<HistoricoIngresos> listarHistorico () throws Exception{
		return historicoIngresosService.listarHistorico();
	}
	
	@PostMapping("/historico")
	public HistoricoIngresos guardarHistorico(@RequestBody HistoricoIngresos historico) throws Exception{
		return historicoIngresosService.guardarHistorico(historico);
	}
	
	@PutMapping("/historico")
	public HistoricoIngresos modificarHistorico(@RequestBody HistoricoIngresos historico) throws Exception{
		return historicoIngresosService.modificarHistorico(historico);
	}
	
	@DeleteMapping("/historico/{id}")
	public void eliminarHistorico(@PathVariable Integer id) throws Exception{
		historicoIngresosService.eliminarHistorico(id);
	}
	
}
