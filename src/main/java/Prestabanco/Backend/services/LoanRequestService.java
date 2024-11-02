package Prestabanco.Backend.services;


import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.repositories.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoanRequestService {
    @Autowired
    LoanRequestRepository loanRequestRepository;

    public LoanRequestEntity getLoanRequestById(Long id) {
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Id nulo o invalido");
        }else {
            return loanRequestRepository.findById(id).get();
        }
    }
    public LoanRequestEntity saveLoanRequest(LoanRequestEntity loanRequest){
        if (loanRequest != null && loanRequest.getId() != null && loanRequest.getAmount() > 0
          && loanRequest.getYears() > 0 && loanRequest.getInterestRate() > 0 && loanRequest.getRut() != ""){
            return loanRequestRepository.save(loanRequest);}
        throw new IllegalArgumentException("Error de campos vacios");
    }

}
