package integracionContinua.services;

import java.util.List;
import java.util.Optional;

import integracionContinua.models.TipoVehiculos;

public interface TipoVehiculosService {

	TipoVehiculos crearTipoVehiculo (TipoVehiculos tipoVehiculo);
	
	Optional<TipoVehiculos> consultarTipoVehiculo (Integer id_tipo);
	
	TipoVehiculos modificarTipoVehiculo (TipoVehiculos tipoVehiculo);
	
	void eliminarTipoVehiculo (Integer id_tipo);
	
	List<TipoVehiculos> listarTipoVehiculos ();
}
