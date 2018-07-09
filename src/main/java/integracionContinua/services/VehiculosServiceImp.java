package integracionContinua.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import integracionContinua.models.Vehiculos;
import integracionContinua.repositories.VehiculosRepository;

@Service
@Transactional
public class VehiculosServiceImp implements VehiculosService{
	
	@Autowired
	private VehiculosRepository vehiculoRepository;

	@Override
	public Vehiculos crearVehiculo(Vehiculos vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Optional<Vehiculos> consultarVehiculo(String placa) {
		return vehiculoRepository.findById(placa);
	}

	@Override
	public Vehiculos modificarVehiculo(Vehiculos vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVehiculo(String placa) {
		vehiculoRepository.deleteById(placa);
	}

	@Override
	public List<Vehiculos> listarVehidulos() {
		return vehiculoRepository.findAll();
	}

}
