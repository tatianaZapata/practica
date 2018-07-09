package integracionContinua.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TipoVehiculos {
	
	@Id
	@GeneratedValue
	private Integer id_tipo;
	
	private String descripcion;

	
	public Integer getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(Integer id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
