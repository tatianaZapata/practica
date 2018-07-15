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
import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.models.HistoricoIngresos;
import com.estacionamiento.models.TipoVehiculo.TipoDeVehiculo;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.repositories.HistoricoIngresosRepository;
import com.estacionamiento.repositories.VehiculosRepository;

@Service
@Transactional
public class VehiculosServiceImp implements VehiculosService {

	private static final Logger LOGGER = Logger.getLogger(VehiculosController.class.getName());

	@Autowired
	private VehiculosRepository vehiculoRepository;

	@Autowired
	private HistoricoIngresosRepository historicoIngresosRepository;

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
			
			Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(vehiculo.getPlaca());
			if (vehiculoExistente.isPresent()) {
				if(vehiculoExistente.get().isEstado() == true) {
					throw new Exception("El vehiculo ya se encuentra en el parqueadero");
				}
			}
			vehiculo.setEstado(true);
			vehiculo.setFechaIngreso(LocalDateTime.now());
			vehiculo = vehiculoRepository.save(vehiculo);
			
			//Guardar historico
			HistoricoIngresos historico = new HistoricoIngresos();
			historico.setFechaIngreso(vehiculo.getFechaIngreso());
			historico.setPlaca(vehiculo.getPlaca());
			historicoIngresosRepository.save(historico);
			
			return vehiculo;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Optional<Vehiculo> consultarVehiculo(String placa) throws Exception{
		return vehiculoRepository.findById(placa);
	}

	@Override
	public Vehiculo modificarVehiculo(Vehiculo vehiculo) throws Exception{
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVehiculo(String placa) throws Exception{
		vehiculoRepository.deleteById(placa);
	}

	@Override
	public List<Vehiculo> listarVehidulos() throws Exception {
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
			throw e;
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
	public TotalAPagarDTO calcularTotalAPagar(String placa) throws Exception {
		BigDecimal totalAPagar = BigDecimal.ZERO;
		try {
			Optional<Vehiculo> vehiculo = vehiculoRepository.findById(placa);
			if (!vehiculo.isPresent()) {
				throw new Exception("No existe un vehiculo con esa placa");
			}

			if (vehiculo.get().isEstado() == false) {
				throw new Exception("El vehiculo no se encuentra parqueado");
			}

			// Se actualiza la fecha de salida
			vehiculo.get().setFechaSalida(LocalDateTime.now());
			vehiculo.get().setEstado(false);

			LocalDateTime fechaIngreso = vehiculo.get().getFechaIngreso();
			LocalDateTime fechaSalida = vehiculo.get().getFechaSalida();
			Duration duracion = Duration.between(fechaIngreso, fechaSalida);
			Long minutosTranscurridos = duracion.toMinutes();
			Double tiempoTranscurrido = minutosTranscurridos / 60D;
			Integer horasTranscurridas = tiempoTranscurrido.intValue();
			boolean sumarMinutos = (tiempoTranscurrido > horasTranscurridas) ? true : false;
			if(sumarMinutos) {
				horasTranscurridas += 1;
			}

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
				} else if (horasTranscurridas >= 9 && horasTranscurridas <= 24) {
					// Cobrar por dia
					totalAPagar = Constants.VALOR_DIA_MOTO;
				} else {
					// Calcular dias y horas
					BigDecimal cantidadDias = new BigDecimal(horasTranscurridas).divide(new BigDecimal(24), 0,
							RoundingMode.HALF_UP);
					BigDecimal cantidadHoras = new BigDecimal(horasTranscurridas).remainder(new BigDecimal(24));
					BigDecimal totalValorDias = cantidadDias.multiply(Constants.VALOR_DIA_MOTO);
					BigDecimal totalValorHoras = cantidadHoras.multiply(Constants.VALOR_HORA_MOTO);
					totalAPagar = totalValorDias.add(totalValorHoras);
				}
				if (vehiculo.get().getCilindraje() > 500) {
					totalAPagar = totalAPagar.add(new BigDecimal(2000));
				}
			}

			// Actualizar historico
			HistoricoIngresos historico = historicoIngresosRepository
					.findTop1ByPlacaOrderByFechaIngresoDesc(vehiculo.get().getPlaca());
			historico.setFechaSalida(vehiculo.get().getFechaSalida());
			historico.setPrecio(totalAPagar);
			
			return new TotalAPagarDTO(totalAPagar, placa, horasTranscurridas);

		} catch (Exception e) {
			LOGGER.info(" -------------------- Error en calcularTotalAPagar: " + e);
			throw e;
		}
	}

}
