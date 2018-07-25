package com.estacionamiento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.modelsTest.VehiculoTestDataBuilder;
import com.estacionamiento.repositories.VehiculosRepository;
import com.estacionamiento.services.VehiculosServiceImp;

public class VehiculoValidacionesTest {
	
	@InjectMocks
	private VehiculosServiceImp vehiculosServiceImp;
	
	@Mock
	private VehiculosRepository vehiculoRepository;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void probarParqueaderoSinEspacioParaCarros() throws ParseException{
		//Arrange
			boolean hayCapacidad;
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withCodigoTipoVehiculo("CARRO").build();
			Mockito.when(vehiculoRepository.contarPorTipoVehiculo(vehiculo.getCodigoTipoVehiculo())).thenReturn(20);
		//Act
			hayCapacidad = vehiculosServiceImp.verificarCapacidad(vehiculo);
		//Assert
			assertEquals(false, hayCapacidad);
	}
	
	@Test
	public void probarParqueaderoConEspacioParaCarros() throws ParseException{
		//Arrange
			boolean hayCapacidad;
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withCodigoTipoVehiculo("CARRO").build();
			Mockito.when(vehiculoRepository.contarPorTipoVehiculo(vehiculo.getCodigoTipoVehiculo())).thenReturn(19);
		//Act
			hayCapacidad = vehiculosServiceImp.verificarCapacidad(vehiculo);
		//Assert
			assertEquals(true, hayCapacidad);
	}
	
	@Test
	public void probarParqueaderoSinEspacioParaMotos() throws ParseException{
		//Arrange
			boolean hayCapacidad;
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withCodigoTipoVehiculo("MOTO").build();
			Mockito.when(vehiculoRepository.contarPorTipoVehiculo(vehiculo.getCodigoTipoVehiculo())).thenReturn(10);
		//Act
			hayCapacidad = vehiculosServiceImp.verificarCapacidad(vehiculo);
		//Assert
			assertEquals(false, hayCapacidad);
	}
	
	@Test
	public void probarParqueaderoConEspacioParaMotos() throws ParseException{
		//Arrange
			boolean hayCapacidad;
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withCodigoTipoVehiculo("MOTO").build();
			Mockito.when(vehiculoRepository.contarPorTipoVehiculo(vehiculo.getCodigoTipoVehiculo())).thenReturn(9);
		//Act
			hayCapacidad = vehiculosServiceImp.verificarCapacidad(vehiculo);
		//Assert
			assertEquals(true, hayCapacidad);
	}
	
	@Test
	public void probarDiaPermitido() throws ParseException{
		//Arrange
			boolean diaPermitidoMetodo;
			boolean diaPermitido = false;
			DayOfWeek diaSemana = LocalDate.now().getDayOfWeek();
			if (diaSemana.equals(DayOfWeek.SUNDAY) || diaSemana.equals(DayOfWeek.MONDAY)) {
				diaPermitido = true;
			}
		//Act
			diaPermitidoMetodo = vehiculosServiceImp.validarDia();
		//Assert
			assertEquals(diaPermitido, diaPermitidoMetodo);
	}
	
	@Test
	public void validarPlacaA() throws ParseException{
		try {
		//Arrange
			Boolean esPlacaA;
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("ATT77E").build();
		//Act
			esPlacaA = vehiculosServiceImp.verificarPlacaA(vehiculo.getPlaca());
			vehiculosServiceImp.crearVehiculo(vehiculo);
		//Assert
			assertTrue(esPlacaA);
		} catch (Exception e) {
			//Assert
			assertEquals("No esta autorizado a ingresar este dia", e.getMessage());
		}
	}
	
	@Test
	public void probarParqueaderoLleno() {
		try {
		//Arrange
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
			Mockito.when(vehiculoRepository.contarPorTipoVehiculo(vehiculo.getCodigoTipoVehiculo())).thenReturn(10);
		//Act
			vehiculosServiceImp.crearVehiculo(vehiculo);
		} catch (Exception e) {
			//Assert
			assertEquals("El parqueadero se encuentra lleno", e.getMessage());
		}
	}
	
}
