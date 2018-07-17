package com.estacionamiento.excepciones;

public class DiaNoPermitido extends RuntimeException {

	public DiaNoPermitido(String mensaje){
		super (mensaje);
	}
}
