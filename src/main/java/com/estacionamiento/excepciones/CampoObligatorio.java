package com.estacionamiento.excepciones;

public class CampoObligatorio extends RuntimeException{

	public CampoObligatorio(String mensaje) {
		super (mensaje);
	}
}
