package integracionContinua.controllers;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import integracionContinua.models.Vehiculos;
import integracionContinua.services.VehiculosService;

@RestController
@RequestMapping("/api")
public class VehiculosController {
	
	@Autowired
	private VehiculosService vehiculoService;

	@PostMapping("/vehiculos")
	public Vehiculos crearVehiculo (@RequestBody Vehiculos vehiculo) {
		System.out.print("------------------- Entró crear -----------------------------");
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
	//@RequestMapping(value = "/listar/", method = RequestMethod.GET)
	public List<Vehiculos> listarVehidulos (){
		System.out.print("------------------- Entró listar -----------------------------");
		return vehiculoService.listarVehidulos();
	}
}
