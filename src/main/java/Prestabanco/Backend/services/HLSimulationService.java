package Prestabanco.Backend.services;


import static java.lang.Math.round;
import static java.lang.Math.pow;
import Prestabanco.Backend.entities.HLSimulationEntity;
import Prestabanco.Backend.repositories.HLSimulationRepository;
import Prestabanco.Backend.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HLSimulationService {
    @Autowired
    HLSimulationRepository hLSimulationRepository;
    UserEntity user;

    public HLSimulationEntity monthlyPaymentCalc (Long id){
        HLSimulationEntity hlSimulation = hLSimulationRepository.findById(id).get();
        float monthlyRate = hlSimulation.getInterestRate()/1200;       // (tasaAnual/12)/100
        int totalMonths = hlSimulation.getYears() * 12;             //plazo en meses
        int loan = hlSimulation.getLoanAmount();                    //total del monto pedido
        float middleCalc = (float) pow((1 + monthlyRate),totalMonths);  // en la formula corresponde a (1+r)^n
        Integer monthlyPayment =  round(loan * ((monthlyRate*middleCalc)/(middleCalc-1)));  //calculo de la cuota mensual


        hlSimulation.setMonthlyPayment(monthlyPayment);  //setear en la entidad
        updateHLSimulation(hlSimulation);   // guardar en la capa de persistencia
        return hlSimulation;

    }
    public ArrayList<HLSimulationEntity> getHLSimulations(String rut) {
        return (ArrayList<HLSimulationEntity>) hLSimulationRepository.findAllByRut(rut);
    }

    public HLSimulationEntity saveHLSimulation(HLSimulationEntity hLSimulation){
        return hLSimulationRepository.save(hLSimulation);
    }

    public HLSimulationEntity getHLSimulationById(Long id){
        return hLSimulationRepository.findById(id).get();
    }



    public HLSimulationEntity updateHLSimulation(HLSimulationEntity hLSimulation) {
        return hLSimulationRepository.save(hLSimulation);
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



