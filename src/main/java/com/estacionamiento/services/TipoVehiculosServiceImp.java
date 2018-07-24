package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.models.TipoVehiculo;
import com.estacionamiento.repositories.TipoVehiculosRepository;

@Service
@Transactional
public class TipoVehiculosServiceImp implements TipoVehiculosService{
	
	@Autowired
	private TipoVehiculosRepository tipoVehiculoRepository;

	@Override
	public TipoVehiculo crearTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return tipoVehiculoRepository.save(tipoVehiculo);
	}

	@Override
	public Optional<TipoVehiculo> consultarTipoVehiculo(String idTipo) {
		return tipoVehiculoRepository.findById(idTipo);
	}

	@Override
	public TipoVehiculo modificarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return tipoVehiculoRepository.save(tipoVehiculo);
	}

	@Override
	public void eliminarTipoVehiculo(String idTipo) {
		tipoVehiculoRepository.deleteById(idTipo);
	}

	@Override
	public List<TipoVehiculo> listarTipoVehiculos() {
		return tipoVehiculoRepository.findAll();
	}

}
