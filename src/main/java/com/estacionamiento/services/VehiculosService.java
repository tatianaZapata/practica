package com.estacionamiento.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.Vehiculo;

public interface VehiculosService {
	
	Vehiculo crearVehiculo (Vehiculo vehiculo) throws Exception;
	
	Optional<Vehiculo> consultarVehiculo (String placa) throws Exception;
	
	Vehiculo modificarVehiculo (Vehiculo vehiculo) throws Exception;
	
	void eliminarVehiculo (String placa) throws Exception;
	
	List<Vehiculo> listarVehidulos () throws Exception;
	
	BigDecimal calcularTotalAPagar (String placa) throws Exception;

}
