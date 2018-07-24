package com.estacionamiento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
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
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			vehiculosServiceImp.crearVehiculo(vehiculo);
		//Assert
			assertTrue(vehiculo.isEstado());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void vehiculoNoExiste() {
		try {
		//Arrange
			Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("000000").build();
		//Act
			vehiculosServiceImp.salirDeEstacionamiento(vehiculo.getPlaca());
		} catch (Exception e) {
			//Assert
			assertEquals("No existe un vehiculo con esa placa", e.getMessage());
		}
	}
}
