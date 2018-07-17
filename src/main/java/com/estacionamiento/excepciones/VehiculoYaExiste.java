package com.estacionamiento.excepciones;

public class VehiculoYaExiste extends RuntimeException{

	public VehiculoYaExiste(String mensaje){
		super (mensaje);
	}
}
