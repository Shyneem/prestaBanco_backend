package Prestabanco.Backend.services;


import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.repositories.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoanRequestService {
    @Autowired
    LoanRequestRepository loanRequestRepository;

    public LoanRequestEntity saveLoanRequest(LoanRequestEntity loanRequest){
        return loanRequestRepository.save(loanRequest);
    }

}
