package com.estacionamiento.modelsTest;

import java.text.ParseException;
import com.estacionamiento.models.TipoVehiculo;

public class TipoVehiculoDataBuilder {

	private String idTipo;
	private String descripcion;
	
	public TipoVehiculoDataBuilder() throws ParseException {
		this.idTipo = "MOTO";
		this.descripcion = "Moto";
	}
	
	public TipoVehiculoDataBuilder withIdTipo(String idTipo) {
		this.idTipo = idTipo;
		return this;
	}
	
	public TipoVehiculoDataBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public TipoVehiculo build() {
		return new TipoVehiculo(idTipo, descripcion);
	}
}
