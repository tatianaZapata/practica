package com.estacionamiento.dto;

import java.math.BigDecimal;

public class TotalAPagarDTO {
	
	private BigDecimal totalPagar;
	private String placa;
	private Integer horasTranscurridas;

	public TotalAPagarDTO() {}

	public TotalAPagarDTO(BigDecimal totalPagar, String placa, Integer horasTranscurridas) {
		super();
		this.totalPagar = totalPagar;
		this.placa = placa;
		this.horasTranscurridas = horasTranscurridas;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getHorasTranscurridas() {
		return horasTranscurridas;
	}

	public void setHorasTranscurridas(Integer horasTranscurridas) {
		this.horasTranscurridas = horasTranscurridas;
	}
	
}
