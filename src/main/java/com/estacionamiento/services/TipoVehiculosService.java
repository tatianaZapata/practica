package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.TipoVehiculo;

public interface TipoVehiculosService {

	TipoVehiculo crearTipoVehiculo (TipoVehiculo tipoVehiculo);
	
	Optional<TipoVehiculo> consultarTipoVehiculo (Integer id_tipo);
	
	TipoVehiculo modificarTipoVehiculo (TipoVehiculo tipoVehiculo);
	
	void eliminarTipoVehiculo (Integer id_tipo);
	
	List<TipoVehiculo> listarTipoVehiculos ();
}