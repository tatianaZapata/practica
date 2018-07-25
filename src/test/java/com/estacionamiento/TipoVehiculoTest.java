package com.estacionamiento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.text.ParseException;
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

import com.estacionamiento.models.TipoVehiculo;
import com.estacionamiento.modelsTest.TipoVehiculoDataBuilder;
import com.estacionamiento.services.TipoVehiculosServiceImp;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@Rollback(value=true)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TipoVehiculoTest {

	@Autowired
	private TipoVehiculosServiceImp tipoVehiculoServiceImp;
	
	@Test
	public void crearTipoVehiculo() {
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
		//Assert
			assertNotNull(tipo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void consultarTipoVehiculo() {
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			Optional<TipoVehiculo> tipoVehiculo = tipoVehiculoServiceImp.consultarTipoVehiculo(tipo.getIdTipo());
		//Assert
			assertNotNull(tipoVehiculo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void modificarTipoVehiculo() {
		try {
		//Arrange
			TipoVehiculo tipoVehiculo;
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			tipo.setDescripcion("Motocicleta");
			tipoVehiculo = tipoVehiculoServiceImp.modificarTipoVehiculo(tipo);
		//Assert
			assertEquals("Motocicleta", tipoVehiculo.getDescripcion());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void eliminarTipoVehiculo() {
		try {
		//Arrange
			TipoVehiculo tipo = new TipoVehiculoDataBuilder().build();
		//Act
			tipoVehiculoServiceImp.crearTipoVehiculo(tipo);
			tipoVehiculoServiceImp.eliminarTipoVehiculo(tipo.getIdTipo());
			Optional<TipoVehiculo> tipoVehiculo = tipoVehiculoServiceImp.consultarTipoVehiculo(tipo.getIdTipo());
		//Assert
			assertThat(!tipoVehiculo.isPresent()).isTrue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
