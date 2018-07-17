package com.estacionamiento.excepciones;

public class VehiculoNoExiste extends RuntimeException {

	public VehiculoNoExiste(String mensaje) {
		super(mensaje);
	}
}
