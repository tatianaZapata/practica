package com.estacionamiento.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.estacionamiento.excepciones.CampoObligatorio;
import com.estacionamiento.excepciones.DiaNoPermitido;
import com.estacionamiento.excepciones.ParqueaderoLleno;
import com.estacionamiento.excepciones.VehiculoNoExiste;
import com.estacionamiento.excepciones.VehiculoYaExiste;

@ControllerAdvice
public class HandlerException {
	
	@ExceptionHandler({ ParqueaderoLleno.class })
	public ResponseEntity<Object> parqueaderoLleno(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ DiaNoPermitido.class })
	public ResponseEntity<Object> diaNoPermitido(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ VehiculoYaExiste.class })
	public ResponseEntity<Object> vehiculoYaExiste(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ VehiculoNoExiste.class })
	public ResponseEntity<Object> vehiculoNoExiste(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ CampoObligatorio.class })
	public ResponseEntity<Object> campoObligatorio(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
