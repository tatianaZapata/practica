package com.estacionamiento;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamiento.models.Vehiculo;
import com.estacionamiento.modelsTest.VehiculoTestDataBuilder;
import com.estacionamiento.repositories.HistoricoIngresosRepository;
import com.estacionamiento.repositories.VehiculosRepository;
import com.estacionamiento.services.VehiculosServiceImp;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class VehiculoOperacionesTest {
	
	@InjectMocks
	private VehiculosServiceImp vehiculosServiceImp;
	
	@Mock
	private VehiculosRepository vehiculoRepository;
	
	@Mock
	private HistoricoIngresosRepository historicoIngresosRepository;
	
	@Test
	public void crearVehiculo() throws ParseException{
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

}
