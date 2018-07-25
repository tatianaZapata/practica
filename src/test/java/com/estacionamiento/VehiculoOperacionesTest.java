package com.estacionamiento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.models.TipoVehiculo;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.modelsTest.TipoVehiculoDataBuilder;
import com.estacionamiento.modelsTest.VehiculoTestDataBuilder;
import com.estacionamiento.services.TipoVehiculosServiceImp;
import com.estacionamiento.services.VehiculosServiceImp;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@Rollback(value=true)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VehiculoOperacionesTest {
	
	@Autowired
	private VehiculosServiceImp vehiculosServiceImp;
	
	@Autowired
	private TipoVehiculosServiceImp tipoVehiculoServiceImp;
	
	@Test
	public void ingresarAEstacionamiento() throws ParseException{
	//Arrange
		TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
	//Act
		tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
		vehiculosServiceImp.crearVehiculo(vehiculo);
	//Assert
		assertTrue(vehiculo.isEstado());
	}
	
	@Test
	public void salirDeEstacionamiento() {
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
			TotalAPagarDTO totalDTO;
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
			totalDTO = vehiculosServiceImp.salirDeEstacionamiento(vehiculo.getPlaca());
		//Assert
			assertNotNull(totalDTO.getTotalPagar());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void probarSalirVehiculoQueNoExiste() {
		try {
		//Arrange
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("000000").build();
		//Act
			vehiculosServiceImp.salirDeEstacionamiento(vehiculo.getPlaca());
		} catch (Exception e) {
			//Assert
			assertEquals("El vehiculo no se encuentra en el parqueadero", e.getMessage());
		}
	}
	
	@Test
	public void probarIngresarVehiculoPresente() {
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
		} catch (Exception e) {
			//Assert
			assertEquals("El vehiculo ya se encuentra en el parqueadero", e.getMessage());
		}
	}
	
	@Test
	public void probarConsultarVehiculo() {
		try {
		//Arrange
			Optional<Vehiculo> vehiculoConsultado = null;
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
			vehiculoConsultado = vehiculosServiceImp.consultarVehiculo(vehiculo.getPlaca());
		//Assert
			assertNotNull(vehiculoConsultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void probarModificarVehiculo() {
		try {
		//Arrange
			Vehiculo vehiculoModificado;
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
			vehiculo.setCodigoTipoVehiculo("CARRO");
			vehiculoModificado = vehiculosServiceImp.modificarVehiculo(vehiculo);
		//Assert
			assertEquals("CARRO", vehiculoModificado.getCodigoTipoVehiculo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void probarEliminarVehiculo() {
		try {
		//Arrange
			Optional<Vehiculo> vehiculoConsultado;
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
			vehiculosServiceImp.eliminarVehiculo(vehiculo.getPlaca());
			vehiculoConsultado = vehiculosServiceImp.consultarVehiculo(vehiculo.getPlaca());
		//Assert
			assertThat(!vehiculoConsultado.isPresent()).isTrue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void probarListarVehiculo() {
		try {
		//Arrange
			List<Vehiculo> listaVehiculos;
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo1 = new VehiculoTestDataBuilder().build();
			Vehiculo vehiculo2 = new VehiculoTestDataBuilder().withPlaca("TRE441").build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo1);
			vehiculosServiceImp.crearVehiculo(vehiculo2);
			listaVehiculos = vehiculosServiceImp.listarVehidulos();
		//Assert
			assertEquals(2, listaVehiculos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
