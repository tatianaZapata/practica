package integracionContinua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import integracionContinua.models.Vehiculos;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, String>{

}
