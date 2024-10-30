package Prestabanco.Backend.repositories;


import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.entities.LoanRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequestEntity, Long> {
}
