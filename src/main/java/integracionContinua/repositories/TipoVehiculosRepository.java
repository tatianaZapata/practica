package integracionContinua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import integracionContinua.models.TipoVehiculos;

@Repository
public interface TipoVehiculosRepository extends JpaRepository<TipoVehiculos, Integer>{

}
