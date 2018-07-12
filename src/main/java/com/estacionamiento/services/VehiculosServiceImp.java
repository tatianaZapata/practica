package com.estacionamiento.services;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.controllers.VehiculosController;
import com.estacionamiento.models.TipoVehiculo.TipoDeVehiculo;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.repositories.VehiculosRepository;

@Service
@Transactional
public class VehiculosServiceImp implements VehiculosService{
	
	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());
	
	@Autowired
	private VehiculosRepository vehiculoRepository;

	@Override
	public Vehiculo crearVehiculo(Vehiculo vehiculo) {
		try {
			boolean hayCupo = verificarCapacidad(vehiculo);

			if (!hayCupo) {
				LOGGER.info("No hay cupo");
				return null;
			}
			if (vehiculo.getPlaca().startsWith("A") || vehiculo.getPlaca().startsWith("a")) {
				boolean diaPermitido = validarDia();
				if (!diaPermitido) {
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
	public Optional<Vehiculo> consultarVehiculo(String placa) {
		return vehiculoRepository.findById(placa);
	}

	@Override
	public Vehiculo modificarVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVehiculo(String placa) {
		vehiculoRepository.deleteById(placa);
	}

	@Override
	public List<Vehiculo> listarVehidulos() {
		return vehiculoRepository.findAll();
	}
	
	public List<Vehiculo> listarIngresosDia() {
		return vehiculoRepository.findByFechaIngreso(LocalDateTime.now());
	}
	
	private boolean verificarCapacidad(Vehiculo vehiculo) {
		try {
			boolean hayCupo = false;
			Integer quantity = 0;
			
			switch(vehiculo.getCodigoTipoVehiculo()) {
				case "CARRO":
					quantity = vehiculoRepository.findByCodigoTipoVehiculo(TipoDeVehiculo.CARRO.name()).size();
					if(quantity < 20)
						hayCupo = true;
					break;
					
				case "MOTO": 
					quantity = vehiculoRepository.findByCodigoTipoVehiculo(TipoDeVehiculo.MOTO.name()).size();
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
		DayOfWeek diaSemana = LocalDate.now().getDayOfWeek();
		if(diaSemana.equals(DayOfWeek.SUNDAY) ||  diaSemana.equals(DayOfWeek.MONDAY)) {
			diaValido = true;
		}
		return diaValido;
	}

	@Override
	public BigDecimal calcularTotalAPagar(String placa) {
		Optional<Vehiculo> vehiculo = vehiculoRepository.findById(placa);
		if(!vehiculo.isPresent()) {
			return BigDecimal.ZERO;
		}
		if(vehiculo.get().getCodigoTipoVehiculo().equals(TipoDeVehiculo.CARRO.name())) {
			LocalDateTime fechaIngreso = vehiculo.get().getFechaIngreso();
			LocalDateTime fechaSalida = LocalDateTime.now();
			Duration duracion = Duration.between(fechaIngreso, fechaSalida);
			Long horasTranscurridas = duracion.toHours();
			
			if(horasTranscurridas < 9) {
				//Cobrar por horas
				
			}else if(horasTranscurridas >= 9 && horasTranscurridas <= 24){
				//Cobrar por dia
				
			}else{
				//Calcular dias y horas
				
			}
		}
		return null;
	}

}
