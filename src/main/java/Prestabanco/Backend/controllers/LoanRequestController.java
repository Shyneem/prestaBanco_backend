package Prestabanco.Backend.controllers;


import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loanRequest")
public class LoanRequestController {
    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping("/")
    public ResponseEntity<LoanRequestEntity> save(@RequestBody LoanRequestEntity loanRequestEntity) {
        LoanRequestEntity newLoan = loanRequestService.saveLoanRequest(loanRequestEntity);
        return ResponseEntity.ok(newLoan);
    }

}
