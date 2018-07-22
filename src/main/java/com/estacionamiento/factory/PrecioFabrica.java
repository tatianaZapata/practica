package com.estacionamiento.factory;

import com.estacionamiento.precios.Precio;
import com.estacionamiento.precios.PrecioCarro;
import com.estacionamiento.precios.PrecioMoto;

public class PrecioFabrica {

	public Precio getPrecio(String tipoVehiculo) {
		switch (tipoVehiculo) {
		case "CARRO":
			return new PrecioCarro();
		case "MOTO":
			return new PrecioMoto();
		default:
			return null;
		}
	}
}
