package Prestabanco.Backend.repositories;


import Prestabanco.Backend.entities.HLSimulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HLSimulationRepository extends JpaRepository<HLSimulationEntity, Long>{
    public List<HLSimulationEntity> findAllByRut(String rut);

}
