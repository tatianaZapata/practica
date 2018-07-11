package com.estacionamiento.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.models.Vehiculos;
import com.estacionamiento.services.VehiculosService;

@RestController
@RequestMapping("/api")
public class VehiculosController {
	
	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());
	
	@Autowired
	private VehiculosService vehiculoService;

	@PostMapping("/vehiculos")
	public Vehiculos crearVehiculo (@RequestBody Vehiculos vehiculo) {
		return vehiculoService.crearVehiculo(vehiculo);
	}
	
	@GetMapping("/vehiculos/{placa}")
	public Optional<Vehiculos> consultarVehiculo (@PathVariable String placa){
		return vehiculoService.consultarVehiculo(placa);
	}
	
	@PutMapping("/vehiculos")
	public Vehiculos modificarVehiculo (@RequestBody Vehiculos vehiculo) {
		return vehiculoService.modificarVehiculo(vehiculo);
	}
	
	@DeleteMapping("/vehiculos/{placa}")
	public void eliminarVehiculo (@PathVariable String placa) {
		vehiculoService.eliminarVehiculo(placa);
	}	
	
	@GetMapping("/vehiculos")
	public ResponseEntity<List<Vehiculos>> listarVehidulos (){
		return new ResponseEntity<>(vehiculoService.listarVehidulos(), HttpStatus.OK);
	}
}
