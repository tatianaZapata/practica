package com.estacionamiento.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.estacionamiento.constants.Constants;
import com.estacionamiento.controllers.VehiculosController;
import com.estacionamiento.models.TipoVehiculo.TipoDeVehiculo;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.repositories.VehiculosRepository;

@Service
@Transactional
public class VehiculosServiceImp implements VehiculosService {

	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());

	@Autowired
	private VehiculosRepository vehiculoRepository;

	@Override
	public Vehiculo crearVehiculo(Vehiculo vehiculo) throws Exception {
		try {
			boolean hayCupo = verificarCapacidad(vehiculo);
	
			if (!hayCupo) {
				LOGGER.info("No hay cupo");
				throw new Exception("No hay cupo");
			}
			if (vehiculo.getPlaca().startsWith("A") || vehiculo.getPlaca().startsWith("a")) {
				boolean diaPermitido = validarDia();
				if (!diaPermitido) {
					LOGGER.info("No esta autorizado a ingresar este dia");
					throw new Exception("No esta autorizado a ingresar este dia");
				}
			}
			return vehiculoRepository.save(vehiculo);
		} catch (Exception e) {
			throw e;
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

			switch (vehiculo.getCodigoTipoVehiculo()) {
			case "CARRO":
				quantity = vehiculoRepository.findByCodigoTipoVehiculo(TipoDeVehiculo.CARRO.name()).size();
				if (quantity < 20)
					hayCupo = true;
				break;

			case "MOTO":
				quantity = vehiculoRepository.findByCodigoTipoVehiculo(TipoDeVehiculo.MOTO.name()).size();
				if (quantity < 10)
					hayCupo = true;
				break;

			default:
				break;
			}
			return hayCupo;
		} catch (Exception e) {
			LOGGER.info(" -------------------- Error en verificarCapacidad: " + e);
			return false;
		}
	}

	private boolean validarDia() {
		boolean diaValido = false;
		DayOfWeek diaSemana = LocalDate.now().getDayOfWeek();
		if (diaSemana.equals(DayOfWeek.SUNDAY) || diaSemana.equals(DayOfWeek.MONDAY)) {
			diaValido = true;
		}
		return diaValido;
	}

	@Override
	public BigDecimal calcularTotalAPagar(String placa) {
		try {

			BigDecimal totalAPagar = BigDecimal.ZERO;

			Optional<Vehiculo> vehiculo = vehiculoRepository.findById(placa);
			if (!vehiculo.isPresent()) {
				throw new Exception("No existe un vehiculo con esa placa");
			}

			// Se actualiza la fecha de salida
			vehiculo.get().setFechaSalida(LocalDateTime.now());
			vehiculoRepository.save(vehiculo.get());

			LocalDateTime fechaIngreso = vehiculo.get().getFechaIngreso();
			LocalDateTime fechaSalida = vehiculo.get().getFechaSalida();
			Duration duracion = Duration.between(fechaIngreso, fechaSalida);
			Long horasTranscurridas = duracion.toHours();

			if (vehiculo.get().getCodigoTipoVehiculo().equals(TipoDeVehiculo.CARRO.name())) {
				if (horasTranscurridas < 9) {
					// Cobrar por horas
					totalAPagar = new BigDecimal(horasTranscurridas).multiply(Constants.VALOR_HORA_CARRO);
				} else if (horasTranscurridas >= 9 && horasTranscurridas <= 24) {
					// Cobrar por dia
					totalAPagar = Constants.VALOR_DIA_CARRO;
				} else {
					// Calcular dias y horas
					BigDecimal cantidadDias = new BigDecimal(horasTranscurridas).divide(new BigDecimal(24));
					BigDecimal cantidadHoras = new BigDecimal(horasTranscurridas).remainder(new BigDecimal(24));
					BigDecimal totalValorDias = cantidadDias.multiply(Constants.VALOR_DIA_CARRO);
					BigDecimal totalValorHoras = cantidadHoras.multiply(Constants.VALOR_HORA_CARRO);
					totalAPagar = totalValorDias.add(totalValorHoras);
				}

			} else if (vehiculo.get().getCodigoTipoVehiculo().equals(TipoDeVehiculo.MOTO.name())) {
				if (horasTranscurridas < 9) {
					// Cobrar por horas
					totalAPagar = new BigDecimal(horasTranscurridas).multiply(Constants.VALOR_HORA_MOTO);
					if (vehiculo.get().getCilindraje() > 500) {
						totalAPagar = totalAPagar.add(new BigDecimal(2000));
					}
				} else if (horasTranscurridas >= 9 && horasTranscurridas <= 24) {
					// Cobrar por dia
					totalAPagar = Constants.VALOR_DIA_MOTO;
					if (vehiculo.get().getCilindraje() > 500) {
						totalAPagar = totalAPagar.add(new BigDecimal(2000));
					}
				} else {
					// Calcular dias y horas
					BigDecimal cantidadDias = new BigDecimal(horasTranscurridas).divide(new BigDecimal(24), 0,
							RoundingMode.HALF_UP);
					BigDecimal cantidadHoras = new BigDecimal(horasTranscurridas).remainder(new BigDecimal(24));
					BigDecimal totalValorDias = cantidadDias.multiply(Constants.VALOR_DIA_MOTO);
					BigDecimal totalValorHoras = cantidadHoras.multiply(Constants.VALOR_HORA_MOTO);
					totalAPagar = totalValorDias.add(totalValorHoras);
					if (vehiculo.get().getCilindraje() > 500) {
						totalAPagar = totalAPagar.add(new BigDecimal(2000));
					}
				}
			}
			return totalAPagar;

		} catch (Exception e) {
			LOGGER.info(" -------------------- Error en calcularTotalAPagar: " + e);
			return null;
		}
	}

}
