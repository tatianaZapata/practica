package com.estacionamiento.modelsTest;

import java.text.ParseException;
import java.time.LocalDateTime;

import com.estacionamiento.models.Vehiculo;

public class VehiculoTestDataBuilder {

	private String placa;
	private Integer cilindraje;
	private String codigoTipoVehiculo;
	
	public VehiculoTestDataBuilder() throws ParseException {
		this.placa = "YRP77D";
		this.cilindraje = 100;
		this.codigoTipoVehiculo = "MOTO";
	}
	
	public VehiculoTestDataBuilder withPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder withCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}
	
	public VehiculoTestDataBuilder withCodigoTipoVehiculo(String codigoTipoVehiculo) {
		this.codigoTipoVehiculo = codigoTipoVehiculo;
		return this;
	}
	
	public Vehiculo build() {
		return new Vehiculo(placa, cilindraje, codigoTipoVehiculo, false, null);
	}
	
}
