package com.estacionamiento.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estacionamiento.models.Vehiculos;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, String>{

	List<Vehiculos> findByFechaIngreso(LocalDateTime fechaIngreso);
	
	@Query("SELECT v FROM Vehiculos v JOIN FETCH v.tipoVehiculo tipoveh WHERE tipoveh.descripcion = :tipoVehiculo")
    List<Vehiculos> findByTipo(@Param("tipoVehiculo") String tipoVehiculo);
	
	List<Vehiculos> findByCodigoTipoVehiculo(@Param("tipoVehiculo") String tipoVehiculo);
//	@Query(value = "SELECT * FROM vehiculos veh INNER JOIN tipo_vehiculo tip ON veh.fk_tipo_vehiculo = tip.id_tipo WHERE tip.descripcion = 'moto';", nativeQuery=true)
//    public List<Vehiculos> findByMoto();

}
