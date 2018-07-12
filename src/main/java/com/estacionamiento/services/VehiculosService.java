package com.estacionamiento.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.Vehiculo;

public interface VehiculosService {
	
	Vehiculo crearVehiculo (Vehiculo vehiculo) throws Exception;
	
	Optional<Vehiculo> consultarVehiculo (String placa);
	
	Vehiculo modificarVehiculo (Vehiculo vehiculo);
	
	void eliminarVehiculo (String placa);
	
	List<Vehiculo> listarVehidulos ();
	
	BigDecimal calcularTotalAPagar (String placa);

}
