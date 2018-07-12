package com.estacionamiento.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.Vehiculos;

public interface VehiculosService {
	
	Vehiculos crearVehiculo (Vehiculos vehiculo);
	
	Optional<Vehiculos> consultarVehiculo (String placa);
	
	Vehiculos modificarVehiculo (Vehiculos vehiculo);
	
	void eliminarVehiculo (String placa);
	
	List<Vehiculos> listarVehidulos ();
	
	BigDecimal calcularTotalAPagar (String placa);

}
