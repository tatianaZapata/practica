package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import com.estacionamiento.models.TipoVehiculos;

public interface TipoVehiculosService {

	TipoVehiculos crearTipoVehiculo (TipoVehiculos tipoVehiculo);
	
	Optional<TipoVehiculos> consultarTipoVehiculo (Integer id_tipo);
	
	TipoVehiculos modificarTipoVehiculo (TipoVehiculos tipoVehiculo);
	
	void eliminarTipoVehiculo (Integer id_tipo);
	
	List<TipoVehiculos> listarTipoVehiculos ();
}
