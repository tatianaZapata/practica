package com.estacionamiento.excepciones;

public class ParqueaderoLleno extends RuntimeException{
	
	public ParqueaderoLleno(String mensaje){
		super (mensaje);
	}

}
