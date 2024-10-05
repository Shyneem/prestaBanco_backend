package Prestabanco.Backend.repositories;


import Prestabanco.Backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByRut(String rut);
    public UserEntity findByType(int type);

}
