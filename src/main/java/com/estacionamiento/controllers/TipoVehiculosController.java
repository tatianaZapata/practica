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

import com.estacionamiento.models.TipoVehiculo;
import com.estacionamiento.services.TipoVehiculosService;

@RestController
@RequestMapping("/api")
public class TipoVehiculosController {
	
	@Autowired
	private TipoVehiculosService tipoVehiculosService;

	@GetMapping("/tipo-vehiculos")
	public List<TipoVehiculo> listarTipoVehiculos (){
		return tipoVehiculosService.listarTipoVehiculos();
	}

	@GetMapping("/tipo-vehiculos/{idTipo}")
	public Optional<TipoVehiculo> consultarTipoVehiculo (@PathVariable String idTipo){
		return tipoVehiculosService.consultarTipoVehiculo(idTipo);
	}

	@PostMapping("/tipo-vehiculos")
	public TipoVehiculo crearTipoVehiculo (@RequestBody TipoVehiculo tipoVehiculo) {
		return tipoVehiculosService.crearTipoVehiculo(tipoVehiculo);
	}
	
	@PutMapping("/tipo-vehiculos")
	public TipoVehiculo modificarTipoVehiculo (@RequestBody TipoVehiculo tipoVehiculo) {
		return tipoVehiculosService.modificarTipoVehiculo(tipoVehiculo);
	}
	
	@DeleteMapping("/tipo-vehiculos/{idTipo}")
	public void eliminarTipoVehiculo (@PathVariable String idTipo) {
		tipoVehiculosService.eliminarTipoVehiculo(idTipo);
	}
	
}
