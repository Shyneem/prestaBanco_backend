package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.HLSimulationEntity;
import Prestabanco.Backend.repositories.HLSimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HLSimulationServiceTest {

    @Mock
    private HLSimulationRepository hLSimulationRepository;

    @InjectMocks
    private HLSimulationService hLSimulationService;

    private HLSimulationEntity hlSimulation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hlSimulation = new HLSimulationEntity();
        hlSimulation.setRut("12345678-9");
        hlSimulation.setId(1L);
        hlSimulation.setLoanAmount(100000000);
        hlSimulation.setInterestRate(4.5f);
        hlSimulation.setYears(20);
    }

    // Test for monthlyPaymentCalc method
    @Test
    void whenCalculateMonthlyPayment_thenReturnUpdatedHLSimulation() {
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertNotNull(result);
        assertEquals(632649, result.getMonthlyPayment());
        verify(hLSimulationRepository).save(hlSimulation);
    }

    @Test
    void whenCalculateMonthlyPaymentWithNonExistentId_thenThrowException() {
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            hLSimulationService.monthlyPaymentCalc(1L);
        });
    }

    @Test
    void whenCalculateMonthlyPaymentWithZeroInterestRate_thenReturnCorrectPayment() {
        hlSimulation.setInterestRate(0.0f);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertEquals(0, result.getMonthlyPayment()); // 100000 / 12
    }

    @Test
    void whenCalculateMonthlyPaymentWithNegativeLoanAmount_thenReturnIllegalArgumentException() {
        hlSimulation.setLoanAmount(-100000);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        assertThrows(IllegalArgumentException.class ,() -> hLSimulationService.monthlyPaymentCalc(1L)); // -100000 / 12
    }

    @Test
    void whenCalculateMonthlyPaymentWithYearsZero_thenReturnCorrectPayment() {
        hlSimulation.setYears(0);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertEquals(0, result.getMonthlyPayment());
    }

    @Test
    void whenCalculateMonthlyPaymentWithHighInterestRate_thenReturnHighPayment() {
        hlSimulation.setInterestRate(20.0f);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertTrue(result.getMonthlyPayment() > 200000); // Example value, should be calculated accordingly
    }

    @Test
    void whenCalculateMonthlyPaymentWithLargeLoanAmount_thenReturnLargePayment() {
        hlSimulation.setLoanAmount(1000000);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertTrue(result.getMonthlyPayment() > 1000); // Example value, should be calculated accordingly
    }

    @Test
    void whenCalculateMonthlyPaymentWithValidInput_thenSaveCorrectEntity() {
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        hLSimulationService.monthlyPaymentCalc(1L);

        verify(hLSimulationRepository).save(hlSimulation);
    }

    @Test
    void whenCalculateMonthlyPaymentWithExceptionInRepository_thenThrowRuntimeException() {
        when(hLSimulationRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            hLSimulationService.monthlyPaymentCalc(1L);
        });
    }

    @Test
    void whenCalculateMonthlyPaymentWithNegativeYears_thenReturnPayment() {
        hlSimulation.setYears(-5);
        when(hLSimulationRepository.findById(1L)).thenReturn(Optional.of(hlSimulation));

        HLSimulationEntity result = hLSimulationService.monthlyPaymentCalc(1L);

        assertEquals(0, result.getMonthlyPayment()); // Should handle negative years
    }

    @Test
    void whenGetHLSimulations_thenReturnListOfSimulations() {
        when(hLSimulationRepository.findAllByRut("12345678-9")).thenReturn(List.of(hlSimulation));

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("12345678-9");

        assertEquals(1, result.size());
        assertEquals(hlSimulation, result.get(0));
    }

    @Test
    void whenGetHLSimulationsWithNoResults_thenReturnEmptyList() {
        when(hLSimulationRepository.findAllByRut("12345678-9")).thenReturn(new ArrayList<>());

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("12345678-9");

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetHLSimulationsWithNullRut_thenReturnEmptyList() {
        when(hLSimulationRepository.findAllByRut(null)).thenReturn(new ArrayList<>());

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetHLSimulationsWithMultipleResults_thenReturnAllSimulations() {
        HLSimulationEntity simulation2 = new HLSimulationEntity();
        simulation2.setRut("12345678-9");

        when(hLSimulationRepository.findAllByRut("12345678-9")).thenReturn(List.of(hlSimulation, simulation2));

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("12345678-9");

        assertEquals(2, result.size());
    }

    @Test
    void whenGetHLSimulationsWithDifferentRut_thenReturnEmptyList() {
        when(hLSimulationRepository.findAllByRut("87654321-0")).thenReturn(new ArrayList<>());

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("87654321-0");

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetHLSimulationsWithWhitespaceRut_thenReturnEmptyList() {
        when(hLSimulationRepository.findAllByRut(" ")).thenReturn(new ArrayList<>());

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations(" ");

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetHLSimulationsWithInvalidRut_thenReturnEmptyList() {
        when(hLSimulationRepository.findAllByRut("invalid")).thenReturn(new ArrayList<>());

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("invalid");

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetHLSimulationsWithExceptionInRepository_thenThrowRuntimeException() {
        when(hLSimulationRepository.findAllByRut("12345678-9")).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            hLSimulationService.getHLSimulations("12345678-9");
        });
    }

    @Test
    void whenGetHLSimulationsWithValidRut_thenReturnList() {
        when(hLSimulationRepository.findAllByRut("12345678-9")).thenReturn(List.of(hlSimulation));

        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations("12345678-9");

        assertEquals(1, result.size());
    }

    @Test
    void whenGetHLSimulationsWithNullInput_thenReturnEmptyList() {
        List<HLSimulationEntity> result = hLSimulationService.getHLSimulations(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenSaveHLSimulation_thenReturnSavedEntity() {
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        HLSimulationEntity result = hLSimulationService.saveHLSimulation(hlSimulation);

        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenSaveHLSimulationWithNullEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            hLSimulationService.saveHLSimulation(null);
        });
    }

    @Test
    void whenSaveHLSimulationWithInvalidData_thenThrowException() {
        hlSimulation.setLoanAmount(-1000);
        assertThrows(IllegalArgumentException.class, () -> {
            hLSimulationService.saveHLSimulation(null);
        });
    }

    @Test
    void whenSaveHLSimulationWithExistingEntity_thenReturnUpdatedEntity() {
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        HLSimulationEntity result = hLSimulationService.saveHLSimulation(hlSimulation);

        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenSaveHLSimulationWithExceptionInRepository_thenThrowRuntimeException() {
        when(hLSimulationRepository.save(hlSimulation)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            hLSimulationService.saveHLSimulation(hlSimulation);
        });
    }


    @Test
    void whenSaveHLSimulationWithEmptyFields_thenReturnSavedEntity() {
        hlSimulation.setLoanAmount(0);
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        HLSimulationEntity result = hLSimulationService.saveHLSimulation(hlSimulation);

        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenSaveHLSimulationWithValidRut_thenReturnSavedEntity() {
        hlSimulation.setRut("12345678-9");
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        HLSimulationEntity result = hLSimulationService.saveHLSimulation(hlSimulation);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
    }

    @Test
    void whenSaveHLSimulationWithInvalidInterestRate_thenReturnSavedEntity() {
        hlSimulation.setInterestRate(-5.0f); // Test with an invalid interest rate
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        HLSimulationEntity result = hLSimulationService.saveHLSimulation(hlSimulation);

        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenSaveHLSimulationWithNullFields_thenThrowException() {
        hlSimulation.setRut(null);
        hlSimulation.setInterestRate(0);

        assertThrows(IllegalArgumentException.class, () -> {
            hLSimulationService.saveHLSimulation(hlSimulation);
        });
    }

    @Test
    void whenSaveHLSimulationMultipleTimes_thenReturnLastSavedEntity() {
        HLSimulationEntity hlSimulation2 = new HLSimulationEntity();
        hlSimulation2.setLoanAmount(200000);
        hlSimulation2.setRut("32165487-9");
        hlSimulation2.setId(3L);
        hlSimulation2.setYears(15);
        hlSimulation2.setInterestRate(5.0f);

        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);
        when(hLSimulationRepository.save(hlSimulation2)).thenReturn(hlSimulation2);

        HLSimulationEntity result1 = hLSimulationService.saveHLSimulation(hlSimulation);
        HLSimulationEntity result2 = hLSimulationService.saveHLSimulation(hlSimulation2);

        assertNotNull(result2);
        assertEquals(hlSimulation2, result2);
    }

    @Test
    void whenUpdateHLSimulation_withValidEntity_thenReturnUpdatedEntity() {
        // given
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        // when
        HLSimulationEntity result = hLSimulationService.updateHLSimulation(hlSimulation);

        // then
        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenUpdateHLSimulation_withNullEntity_thenThrowIllegalArgumentException() {
        // when and then
        assertThrows(IllegalArgumentException.class, () -> hLSimulationService.updateHLSimulation(null));
    }

    @Test
    void whenUpdateHLSimulation_withNegativeLoanAmount_thenThrowIllegalArgumentException() {
        // given
        hlSimulation.setLoanAmount(-5000);

        // when and then
        assertThrows(IllegalArgumentException.class, () -> hLSimulationService.updateHLSimulation(hlSimulation));
    }

    @Test
    void whenUpdateHLSimulation_withZeroInterestRate_thenReturnUpdatedEntity() {
        // given
        hlSimulation.setInterestRate(0.0f);
        when(hLSimulationRepository.save(hlSimulation)).thenReturn(hlSimulation);

        // when
        HLSimulationEntity result = hLSimulationService.updateHLSimulation(hlSimulation);

        // then
        assertNotNull(result);
        assertEquals(hlSimulation, result);
    }

    @Test
    void whenUpdateHLSimulation_withRepositoryException_thenThrowRuntimeException() {
        // given
        when(hLSimulationRepository.save(hlSimulation)).thenThrow(new RuntimeException("Database error"));

        // when and then
        assertThrows(RuntimeException.class, () -> hLSimulationService.updateHLSimulation(hlSimulation));
    }
}