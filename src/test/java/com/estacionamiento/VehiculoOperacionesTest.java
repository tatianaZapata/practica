package com.estacionamiento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.estacionamiento.dto.TotalAPagarDTO;
import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.modelsTest.VehiculoTestDataBuilder;
import com.estacionamiento.repositories.HistoricoIngresosRepository;
import com.estacionamiento.repositories.VehiculosRepository;
import com.estacionamiento.services.VehiculosServiceImp;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class VehiculoOperacionesTest {
	
	@Autowired
	private VehiculosServiceImp vehiculosServiceImp;
	
	@Autowired
	private VehiculosRepository vehiculoRepository;
	
	@Autowired
	private HistoricoIngresosRepository historicoIngresosRepository;
	
	@Test
	public void ingresarAEstacionamiento() throws ParseException{
		try {
		//Arrange
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		//Act
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
			Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
			TotalAPagarDTO totalDTO;
		//Act
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
