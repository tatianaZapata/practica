package integracionContinua.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehiculos {

	@Id
	private String placa;
	
	private Integer cilindraje;
	
	private Integer tipo_vehiculo;
	
	
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
	
	public Integer getTipo_vehiculo() {
		return tipo_vehiculo;
	}
	
	public void setTipo_vehiculo(Integer tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}
	
}
