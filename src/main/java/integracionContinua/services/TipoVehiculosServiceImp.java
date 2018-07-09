package integracionContinua.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import integracionContinua.models.TipoVehiculos;
import integracionContinua.repositories.TipoVehiculosRepository;

@Service
@Transactional
public class TipoVehiculosServiceImp implements TipoVehiculosService{
	
	@Autowired
	private TipoVehiculosRepository tipoVehiculoRepository;

	@Override
	public TipoVehiculos crearTipoVehiculo(TipoVehiculos tipoVehiculo) {
		return tipoVehiculoRepository.save(tipoVehiculo);
	}

	@Override
	public Optional<TipoVehiculos> consultarTipoVehiculo(Integer id_tipo) {
		return tipoVehiculoRepository.findById(id_tipo);
	}

	@Override
	public TipoVehiculos modificarTipoVehiculo(TipoVehiculos tipoVehiculo) {
		return tipoVehiculoRepository.save(tipoVehiculo);
	}

	@Override
	public void eliminarTipoVehiculo(Integer id_tipo) {
		tipoVehiculoRepository.deleteById(id_tipo);
	}

	@Override
	public List<TipoVehiculos> listarTipoVehiculos() {
		return tipoVehiculoRepository.findAll();
	}

}
