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
import com.estacionamiento.controllers.VehiculosController;
import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.excepciones.CampoObligatorio;
import com.estacionamiento.excepciones.DiaNoPermitido;
import com.estacionamiento.excepciones.InternalException;
import com.estacionamiento.excepciones.ParqueaderoLleno;
import com.estacionamiento.excepciones.VehiculoNoExiste;
import com.estacionamiento.excepciones.VehiculoYaExiste;
import com.estacionamiento.factory.PrecioFabrica;
import com.estacionamiento.models.HistoricoIngresos;
import com.estacionamiento.models.TipoVehiculo.TipoDeVehiculo;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.precios.Precio;
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
	public Vehiculo crearVehiculo(Vehiculo vehiculo){
		validarCampos(vehiculo);
		boolean hayCupo = verificarCapacidad(vehiculo);
		if (!hayCupo) {
			throw new ParqueaderoLleno("El parqueadero se encuentra lleno");
		}
		boolean placaIniciaPorA = verificarPlacaA(vehiculo.getPlaca());
		if (placaIniciaPorA) {
			boolean diaPermitido = validarDia();
			if (!diaPermitido) {
				throw new DiaNoPermitido("No esta autorizado a ingresar este dia");
			}
		}
		
		Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(vehiculo.getPlaca());
		if (vehiculoExistente.isPresent() && vehiculoExistente.get().isEstado()) {
			throw new VehiculoYaExiste("El vehiculo ya se encuentra en el parqueadero");
		}
		vehiculo.setEstado(true);
		vehiculo.setFechaIngreso(LocalDateTime.now());
		vehiculoRepository.save(vehiculo);
		
		//Guardar historico
		HistoricoIngresos historico = new HistoricoIngresos();
		historico.setFechaIngreso(vehiculo.getFechaIngreso());
		historico.setPlaca(vehiculo.getPlaca());
		historicoIngresosRepository.save(historico);
		
		return vehiculo;
	}

	@Override
	public Optional<Vehiculo> consultarVehiculo(String placa){
		return vehiculoRepository.findById(placa);
	}

	@Override
	public Vehiculo modificarVehiculo(Vehiculo vehiculo){
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVehiculo(String placa){
		vehiculoRepository.deleteById(placa);
	}

	@Override
	public List<Vehiculo> listarVehidulos(){
		return vehiculoRepository.findByEstado(true);
	}

	public boolean verificarCapacidad(Vehiculo vehiculo) {
		boolean hayCupo = false;
		Integer quantity = 0;

		switch (vehiculo.getCodigoTipoVehiculo()) {
		case "CARRO":
			quantity = vehiculoRepository.contarPorTipoVehiculo(TipoDeVehiculo.CARRO.name());
			if (quantity < 20)
				hayCupo = true;
			break;

		case "MOTO":
			quantity = vehiculoRepository.contarPorTipoVehiculo(TipoDeVehiculo.MOTO.name());
			if (quantity < 10)
				hayCupo = true;
			break;

		default:
			break;
		}
		return hayCupo;
	}

	public boolean validarDia() {
		boolean diaValido = false;
		LocalDate fechaActual = getFechaActual();
		if (fechaActual.getDayOfWeek().equals(DayOfWeek.SUNDAY) || fechaActual.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			diaValido = true;
		}
		return diaValido;
	}

	public LocalDate getFechaActual() {
		return LocalDate.now();
	}

	@Override
	public TotalAPagarDTO salirDeEstacionamiento(String placa){
		BigDecimal totalAPagar = BigDecimal.ZERO;
		try {
			Optional<Vehiculo> vehiculo = vehiculoRepository.findById(placa);
			if (!vehiculo.isPresent() || !vehiculo.get().isEstado()) {
				throw new VehiculoNoExiste("El vehiculo no se encuentra en el parqueadero");
			}
			vehiculo.get().setEstado(false);
			// Actualizar historico
			HistoricoIngresos historico = historicoIngresosRepository
					.findTop1ByPlacaOrderByFechaIngresoDesc(vehiculo.get().getPlaca())
					.orElseThrow(() -> new InternalException("El vehiculo no se encuentra en la tabla de historicos"));
			
			historico.setFechaSalida(LocalDateTime.now());
			Integer horasTranscurridas = calcularHorasTranscurrias(historico);

			PrecioFabrica fabrica = new PrecioFabrica();
			Precio precio = fabrica.getPrecio(vehiculo.get().getCodigoTipoVehiculo());
			
			if (horasTranscurridas < 9) {
				// Cobrar por horas
				totalAPagar = new BigDecimal(horasTranscurridas).multiply(precio.getValorHora());
			} else if (horasTranscurridas >= 9 && horasTranscurridas <= 24) {
				// Cobrar por dia
				totalAPagar = precio.getValorDia();
			} else {
				// Calcular dias y horas
				BigDecimal cantidadDias = new BigDecimal(horasTranscurridas).divide(new BigDecimal(24), 0, RoundingMode.HALF_UP);
				BigDecimal cantidadHoras = new BigDecimal(horasTranscurridas).remainder(new BigDecimal(24));
				BigDecimal totalValorDias = cantidadDias.multiply(precio.getValorDia());
				BigDecimal totalValorHoras = cantidadHoras.multiply(precio.getValorHora());
				totalAPagar = totalValorDias.add(totalValorHoras);
			}

			if(vehiculo.get().getCodigoTipoVehiculo().equals(TipoDeVehiculo.MOTO.name()) && vehiculo.get().getCilindraje() > 500) {
				totalAPagar = totalAPagar.add(new BigDecimal(2000));
			}
			
			historico.setPrecio(totalAPagar);
			
			return new TotalAPagarDTO(totalAPagar, placa, horasTranscurridas);

		} catch (Exception e) {
			LOGGER.info(" -------------------- Error en calcularTotalAPagar: " + e);
			throw e;
		}
	}
	
	public boolean validarCampos(Vehiculo vehiculo) {
		boolean camposCompletos = false;
		if(vehiculo.getPlaca() == null || vehiculo.getPlaca().isEmpty()) {
			throw new CampoObligatorio("La placa del vehiculo es obligatoria");
		}
		if(vehiculo.getCilindraje() == null) {
			throw new CampoObligatorio("El cilindraje del vehiculo es obligatorio");
		}
		if(vehiculo.getCodigoTipoVehiculo() == null || vehiculo.getCodigoTipoVehiculo().isEmpty()) {
			throw new CampoObligatorio("El tipo de vehiculo es obligatorio");
		}
		return camposCompletos;
	}
	
	public boolean verificarPlacaA(String placa) {
		boolean esPlacaA = false;
		if (placa.toUpperCase().startsWith("A")) {
			esPlacaA = true;
		}
		return esPlacaA;
	}
	
	public Integer calcularHorasTranscurrias(HistoricoIngresos historico) {
		Integer horasTranscurridas = 0;
		LocalDateTime fechaIngreso = historico.getFechaIngreso();
		LocalDateTime fechaSalida = historico.getFechaSalida();
		Duration duracion = Duration.between(fechaIngreso, fechaSalida);
		Long minutosTranscurridos = duracion.toMinutes();
		Double tiempoTranscurrido = minutosTranscurridos / 60D;
		horasTranscurridas = tiempoTranscurrido.intValue();
		if(tiempoTranscurrido > horasTranscurridas) {
			horasTranscurridas += 1;
		}
		return horasTranscurridas;
	}

}
