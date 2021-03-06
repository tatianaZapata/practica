package com.estacionamiento.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.estacionamiento.models.Vehiculo;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculo, String>{
	
	@Query("SELECT v FROM Vehiculo v JOIN FETCH v.tipoVehiculo tipoveh WHERE tipoveh.descripcion = :tipoVehiculo")
    List<Vehiculo> findByTipo(@Param("tipoVehiculo") String tipoVehiculo);
	
	List<Vehiculo> findByCodigoTipoVehiculo(@Param("tipoVehiculo") String tipoVehiculo);
	
	@Query(value = "Select count(*) from vehiculo where estado = 1 and fk_tipo_vehiculo = :tipoVehiculo", nativeQuery = true)
	Integer contarPorTipoVehiculo(@Param("tipoVehiculo") String tipoVehiculo);
	
	List<Vehiculo> findByEstado(@Param("estado") boolean estado);

}
