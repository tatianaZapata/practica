package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.models.Vehiculo;

public interface VehiculosService {
	
	Vehiculo crearVehiculo (Vehiculo vehiculo) throws Exception;
	
	Optional<Vehiculo> consultarVehiculo (String placa) throws Exception;
	
	Vehiculo modificarVehiculo (Vehiculo vehiculo) throws Exception;
	
	void eliminarVehiculo (String placa) throws Exception;
	
	List<Vehiculo> listarVehidulos () throws Exception;
	
	TotalAPagarDTO calcularTotalAPagar (String placa) throws Exception;

}
