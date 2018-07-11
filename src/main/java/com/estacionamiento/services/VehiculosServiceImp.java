package com.estacionamiento.services;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.controllers.VehiculosController;
import com.estacionamiento.models.Vehiculos;
import com.estacionamiento.repositories.VehiculosRepository;

@Service
@Transactional
public class VehiculosServiceImp implements VehiculosService{
	
	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());
	
	@Autowired
	private VehiculosRepository vehiculoRepository;

	@Override
	public Vehiculos crearVehiculo(Vehiculos vehiculo) {
//		return vehiculoRepository.save(vehiculo);
		try {
			boolean hayCupo = verificarCapacidad(vehiculo);
			char letraInicial = vehiculo.getPlaca().charAt(0);
			
			if(!hayCupo) {
				LOGGER.info("No hay cupo");
				return null;
			}
			if(letraInicial == 'a' || letraInicial == 'A') {
				boolean diaPermitido = validarDia();
				if(!diaPermitido) {
					LOGGER.info("No está autorizado a ingresar este día");
					return null;
				}
			}
			return vehiculoRepository.save(vehiculo);
		} catch (Exception e) {
			LOGGER.info("Error en crearVehiculo: " + e);
			return null;
		}
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
		try {
			Optional<Vehiculos> vehiculo = vehiculoRepository.findById(placa);
			if(vehiculo != null) {
				
			} else {
				LOGGER.info("No existe el vehículo con placa: " + placa);
			}
			vehiculoRepository.deleteById(placa);
		} catch (Exception e) {
			LOGGER.info("Error en eliminarVehiculo: " + e);
		}
	}

	@Override
	public List<Vehiculos> listarVehidulos() {
		return vehiculoRepository.findAll();
	}
	
	public List<Vehiculos> listarIngresosDia() {
		return vehiculoRepository.findByFechaIngreso(LocalDateTime.now());
	}
	
	private boolean verificarCapacidad(Vehiculos vehiculo) {
		try {
			boolean hayCupo = false;
			Integer quantity = 0;
			
			switch(vehiculo.getTipoVehiculo().getDescripcion()) {
				case "carro": 
					quantity = vehiculoRepository.findByTipo("carro").size();
					if(quantity < 20)
						hayCupo = true;
					break;
					
				case "moto": 
					quantity = vehiculoRepository.findByTipo("moto").size();
					if(quantity < 10)
						hayCupo = true;
					break;
					
				default:
					break;
			}
			return hayCupo;
		} catch (Exception e) {
			LOGGER.info("Error en verificarCapacidad");
			return false;
		}
	}
	
	private boolean validarDia() {
		boolean diaValido = false;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		Integer dia = cal.get(Calendar.DAY_OF_WEEK);
		if(dia == 1 || dia == 2)
			diaValido = true;

		return diaValido;
	}

}
