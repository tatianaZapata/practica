package integracionContinua.services;

import java.util.List;
import java.util.Optional;

import integracionContinua.models.Vehiculos;

public interface VehiculosService {
	
	Vehiculos crearVehiculo (Vehiculos vehiculo);
	
	Optional<Vehiculos> consultarVehiculo (String placa);
	
	Vehiculos modificarVehiculo (Vehiculos vehiculo);
	
	void eliminarVehiculo (String placa);
	
	List<Vehiculos> listarVehidulos ();

}
