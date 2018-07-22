package com.estacionamiento.precios;

import java.math.BigDecimal;

public abstract class Precio {

	protected BigDecimal valorHora;
	protected BigDecimal valorDia;
	
	public BigDecimal getValorHora() {
		return valorHora;
	}
	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}
	public BigDecimal getValorDia() {
		return valorDia;
	}
	public void setValorDia(BigDecimal valorDia) {
		this.valorDia = valorDia;
	}
	
	
}
