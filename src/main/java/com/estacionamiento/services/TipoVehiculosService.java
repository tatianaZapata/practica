package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.TipoVehiculo;

public interface TipoVehiculosService {

	TipoVehiculo crearTipoVehiculo (TipoVehiculo tipoVehiculo);
	
	Optional<TipoVehiculo> consultarTipoVehiculo (String idTipo);
	
	TipoVehiculo modificarTipoVehiculo (TipoVehiculo tipoVehiculo);
	
	void eliminarTipoVehiculo (String idTipo);
	
	List<TipoVehiculo> listarTipoVehiculos ();
}
