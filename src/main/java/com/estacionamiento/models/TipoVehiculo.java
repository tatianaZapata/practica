package com.estacionamiento.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoVehiculo {

	@Id
	@Column(name = "id_tipo")
	private String idTipo;

	@Column(name = "descripcion")
	private String descripcion;

	public String getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public enum TipoDeVehiculo {
		CARRO, MOTO
	}

}
