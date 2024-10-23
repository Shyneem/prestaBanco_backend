package Prestabanco.Backend.repositories;
import Prestabanco.Backend.entities.FileUploadEntity;
import Prestabanco.Backend.entities.HLSimulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {
    ArrayList<FileUploadEntity> findAllByRut(String rut);
}
