package com.estacionamiento.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Vehiculo {

	@Id
	private String placa;
	
	@NotNull
	@Column(name = "cilindraje")
	private Integer cilindraje;
	
	@NotNull
	@Column(name = "fk_tipo_vehiculo")
	private String codigoTipoVehiculo;
	
	@NotNull
	@Column(name = "fecha_ingreso")
	private LocalDateTime fechaIngreso;
	
	@Column(name = "fecha_salida")
	private LocalDateTime fechaSalida;
	
	@Column(name = "estado")
	private boolean estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_tipo_vehiculo", insertable = false, updatable = false)
	private TipoVehiculo tipoVehiculo;
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public Integer getCilindraje() {
		return cilindraje;
	}
	
	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getCodigoTipoVehiculo() {
		return codigoTipoVehiculo;
	}

	public void setCodigoTipoVehiculo(String codigoTipoVehiculo) {
		this.codigoTipoVehiculo = codigoTipoVehiculo;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Vehiculo(String placa, @NotNull Integer cilindraje, @NotNull String codigoTipoVehiculo,
			@NotNull LocalDateTime fechaIngreso) {
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.codigoTipoVehiculo = codigoTipoVehiculo;
		this.fechaIngreso = fechaIngreso;
	}

}
