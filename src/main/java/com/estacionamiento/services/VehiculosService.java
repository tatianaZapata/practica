package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.models.Vehiculo;

public interface VehiculosService {
	
	Vehiculo crearVehiculo (Vehiculo vehiculo);
	
	Optional<Vehiculo> consultarVehiculo (String placa);
	
	Vehiculo modificarVehiculo (Vehiculo vehiculo);
	
	void eliminarVehiculo (String placa);
	
	List<Vehiculo> listarVehidulos ();
	
	TotalAPagarDTO salirDeEstacionamiento (String placa);

}
