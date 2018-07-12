package com.estacionamiento.controllers;

import java.math.BigDecimal;
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
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.services.VehiculosService;

@RestController
@RequestMapping("/api")
public class VehiculosController {
	
	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());
	
	@Autowired
	private VehiculosService vehiculoService;

	@GetMapping("/vehiculos")
	public ResponseEntity<List<Vehiculo>> listarVehidulos (){
		return new ResponseEntity<>(vehiculoService.listarVehidulos(), HttpStatus.OK);
	}
	
	@GetMapping("/vehiculos/{placa}")
	public Optional<Vehiculo> consultarVehiculo (@PathVariable String placa){
		return vehiculoService.consultarVehiculo(placa);
	}
	
	@PostMapping("/vehiculos")
	public Vehiculo crearVehiculo (@RequestBody Vehiculo vehiculo) throws Exception {
		return vehiculoService.crearVehiculo(vehiculo);
	}
	
	@PutMapping("/vehiculos")
	public Vehiculo modificarVehiculo (@RequestBody Vehiculo vehiculo) {
		return vehiculoService.modificarVehiculo(vehiculo);
	}
	
	@DeleteMapping("/vehiculos/{placa}")
	public void eliminarVehiculo (@PathVariable String placa) {
		vehiculoService.eliminarVehiculo(placa);
	}	
	
	@GetMapping("/salir/{placa}")
	public BigDecimal salirDeEstacionamiento (@PathVariable String placa){
		return vehiculoService.calcularTotalAPagar(placa);
	}
}
