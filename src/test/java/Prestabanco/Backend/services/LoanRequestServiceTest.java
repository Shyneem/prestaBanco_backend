package Prestabanco.Backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.repositories.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class LoanRequestServiceTest {
    @Mock
    private LoanRequestRepository loanRequestRepository;

    @InjectMocks
    private LoanRequestService loanRequestService;

    private LoanRequestEntity loanRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanRequest = new LoanRequestEntity();
        loanRequest.setId(1L);
        loanRequest.setAmount(1000);
        loanRequest.setYears(10);
        loanRequest.setRut("12345678-9");
        loanRequest.setInterestRate(5.0f);

    }

    @Test
    void whenSaveLoanRequest_thenReturnSavedEntity() {
        when(loanRequestRepository.save(loanRequest)).thenReturn(loanRequest);

        LoanRequestEntity result = loanRequestService.saveLoanRequest(loanRequest);

        assertNotNull(result);
        assertEquals(loanRequest, result);
    }

    @Test
    void whenSaveLoanRequestWithNullEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> loanRequestService.saveLoanRequest(null));
    }

    @Test
    void whenSaveLoanRequest_thenInvokeRepositoryOnce() {
        loanRequestService.saveLoanRequest(loanRequest);
        verify(loanRequestRepository, times(1)).save(loanRequest);
    }

    @Test
    void whenSaveLoanRequestWithNegativeAmount_thenThrowException() {
        loanRequest.setAmount(-1000);
        assertThrows(IllegalArgumentException.class, () -> loanRequestService.saveLoanRequest(loanRequest));

    }

    @Test
    void whenSaveLoanRequestWithExistingId_thenReturnUpdatedEntity() {
        when(loanRequestRepository.save(loanRequest)).thenReturn(loanRequest);

        LoanRequestEntity result = loanRequestService.saveLoanRequest(loanRequest);

        assertEquals(1L, result.getId());
    }

    @Test
    void whenSaveLoanRequestWithExceptionInRepository_thenThrowRuntimeException() {
        when(loanRequestRepository.save(loanRequest)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> loanRequestService.saveLoanRequest(loanRequest));
    }

    @Test
    void whenSaveLoanRequestWithZeroAmount_thenThrowException() {
        loanRequest.setAmount(0);
        assertThrows(IllegalArgumentException.class, () -> loanRequestService.saveLoanRequest(loanRequest));


    }

    @Test
    void whenSaveLoanRequest_thenRepositoryReturnsNonNull() {
        when(loanRequestRepository.save(loanRequest)).thenReturn(loanRequest);

        LoanRequestEntity result = loanRequestService.saveLoanRequest(loanRequest);

        assertNotNull(result);
    }

    @Test
    void whenSaveLoanRequest_thenEnsureCorrectEntitySaved() {
        when(loanRequestRepository.save(loanRequest)).thenReturn(loanRequest);

        LoanRequestEntity result = loanRequestService.saveLoanRequest(loanRequest);

        assertEquals(loanRequest, result);
    }

    @Test
    void whenSaveLoanRequest_thenCorrectRepositoryMethodCalled() {
        loanRequestService.saveLoanRequest(loanRequest);
        verify(loanRequestRepository).save(loanRequest);
    }
    @Test
    void whenGetLoanRequestById_withExistingId_thenReturnLoanRequest() {
        // given
        when(loanRequestRepository.findById(1L)).thenReturn(Optional.of(loanRequest));

        // when
        LoanRequestEntity result = loanRequestService.getLoanRequestById(1L);

        // then
        assertNotNull(result);
        assertEquals(loanRequest, result);
    }

    @Test
    void whenGetLoanRequestById_withNonExistingId_thenThrowNoSuchElementException() {
        // given
        when(loanRequestRepository.findById(2L)).thenReturn(Optional.empty());

        // when and then
        assertThrows(NoSuchElementException.class, () -> loanRequestService.getLoanRequestById(2L));
    }

    @Test
    void whenGetLoanRequestById_withNullId_thenThrowIllegalArgumentException() {
        // when and then
        assertThrows(IllegalArgumentException.class, () -> loanRequestService.getLoanRequestById(null));
    }

    @Test
    void whenGetLoanRequestById_withNegativeId_thenThrowIllegalArgumentException() {
        // when and then
        assertThrows(IllegalArgumentException.class, () -> loanRequestService.getLoanRequestById(-1L));
    }

    @Test
    void whenGetLoanRequestById_withExceptionInRepository_thenThrowRuntimeException() {
        // given
        when(loanRequestRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        // when and then
        assertThrows(RuntimeException.class, () -> loanRequestService.getLoanRequestById(1L));
    }
}
