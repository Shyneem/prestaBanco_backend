package Prestabanco.Backend.services;


import static java.lang.Math.round;
import static java.lang.Math.pow;
import Prestabanco.Backend.entities.HLSimulationEntity;
import Prestabanco.Backend.repositories.HLSimulationRepository;
import Prestabanco.Backend.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HLSimulationService {
    @Autowired
    HLSimulationRepository hLSimulationRepository;


    public HLSimulationEntity monthlyPaymentCalc (Long id){
        HLSimulationEntity hlSimulation = hLSimulationRepository.findById(id).get();
        if (hlSimulation.getInterestRate() <= 0){
            hlSimulation.setMonthlyPayment(0);
            updateHLSimulation(hlSimulation);
            return hlSimulation;
        }

        if (hlSimulation.getYears() <= 0){
            hlSimulation.setMonthlyPayment(0);
            updateHLSimulation(hlSimulation);
            return hlSimulation;
        }
        if(hlSimulation.getLoanAmount() <= 0){
            hlSimulation.setMonthlyPayment(0);
            updateHLSimulation(hlSimulation);
            return hlSimulation;
        }
        double monthlyRate = hlSimulation.getInterestRate()/1200;      // (tasaAnual/12)/100
        double totalMonths = hlSimulation.getYears() * 12;             //plazo en meses
        double loan = hlSimulation.getLoanAmount();                    //total del monto pedido
        double middleCalc =  pow((1 + monthlyRate),totalMonths); // en la formula corresponde a (1+r)^n
        Integer monthlyPayment = Math.toIntExact(round(loan * ((monthlyRate * middleCalc) / (middleCalc - 1))));  //calculo de la cuota mensual

        hlSimulation.setMonthlyPayment(monthlyPayment);  //setear en la entidad
        updateHLSimulation(hlSimulation);   // guardar en la capa de persistencia
        return hlSimulation;

    }
    public List<HLSimulationEntity> getHLSimulations(String rut) {
        return (List<HLSimulationEntity>) hLSimulationRepository.findAllByRut(rut);
    }

    public HLSimulationEntity saveHLSimulation(HLSimulationEntity hLSimulation){
        if(hLSimulation != null  && hLSimulation.getRut() != null
           && !hLSimulation.getRut().isEmpty() && hLSimulation.getLoanAmount() >= 0
            && hLSimulation.getYears() >=0 ){
            hLSimulationRepository.save(hLSimulation);
            return hLSimulation;
        }
        throw new IllegalArgumentException("La simulacion tiene datos nulos, o fuera de lugar");
    }



    public HLSimulationEntity updateHLSimulation(HLSimulationEntity hLSimulation) {
        if (hLSimulation == null){
            throw new IllegalArgumentException("La simulacion es nula");
        }
        if (hLSimulation.getLoanAmount() <= 0){
            throw new IllegalArgumentException("El monto es nulo o inválido");
        }

        try {
            return hLSimulationRepository.save(hLSimulation);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }



    public boolean deleteHLSimulation(Long id) throws Exception {
        try{
            hLSimulationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


}



