package com.estacionamiento.precios;

import java.math.BigDecimal;

public class PrecioCarro extends Precio{

	public PrecioCarro() {
		super.valorDia = new BigDecimal(8000);
		super.valorHora = new BigDecimal(1000);
	}
}
