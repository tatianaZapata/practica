package com.estacionamiento.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class HistoricoIngresos {
	
	@Id
	@Column(name = "id_historico")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHistorico;
	
	@NotNull
	@Column(name = "fk_placa")
	private String placa;
	
	@NotNull
	@Column(name = "fecha_ingreso")
	private LocalDateTime fechaIngreso;
	
	@Column(name = "fecha_salida")
	private LocalDateTime fechaSalida;
	
	@Column(name = "precio")
	private BigDecimal precio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_placa", insertable = false, updatable = false)
	private Vehiculo vehiculoHistorico;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Long getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Vehiculo getVehiculoHistorico() {
		return vehiculoHistorico;
	}

	public void setVehiculoHistorico(Vehiculo vehiculoHistorico) {
		this.vehiculoHistorico = vehiculoHistorico;
	}

	
}
