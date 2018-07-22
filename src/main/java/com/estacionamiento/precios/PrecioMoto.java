package com.estacionamiento.precios;

import java.math.BigDecimal;

public class PrecioMoto extends Precio {

	public PrecioMoto() {
		super.valorDia = new BigDecimal(4000);
		super.valorHora = new BigDecimal(500);
	}
}
