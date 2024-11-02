package Prestabanco.Backend.controllers;



import Prestabanco.Backend.entities.HLSimulationEntity;
import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.services.HLSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
public class HLSimulationController {
    @Autowired
    HLSimulationService hLSimulationService;


    @GetMapping("/")
    public ResponseEntity<List<HLSimulationEntity>> listHLSimulations(@RequestParam ("rut") String rut) {
        List<HLSimulationEntity> hlsimulations = hLSimulationService.getHLSimulations(rut);
        return ResponseEntity.ok(hlsimulations);
    }



    @PostMapping("/monthly-payment")
    public ResponseEntity<HLSimulationEntity> saveHLSimulation(@RequestBody HLSimulationEntity hlSimulation) {
        //Se guarda la entidad en la base de datos
        HLSimulationEntity hlSimulationNew = hLSimulationService.saveHLSimulation(hlSimulation);

        //Aqui se hace el calculo de la cuota mensual
        HLSimulationEntity hlSimulationUpdated = hLSimulationService.monthlyPaymentCalc(hlSimulationNew.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(hlSimulationUpdated);
    }

    @PutMapping("/")
    public ResponseEntity<HLSimulationEntity> updateHLSimulation(@RequestBody HLSimulationEntity hlSimulation){
        HLSimulationEntity hlSimulationUpdated = hLSimulationService.updateHLSimulation(hlSimulation);
        return ResponseEntity.ok(hlSimulationUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteHLSimulationById(@PathVariable Long id) throws Exception {
        var isDeleted = hLSimulationService.deleteHLSimulation(id);
        return ResponseEntity.noContent().build();
    }

   /* @GetMapping("/{id}/monthly-payment/")
    public ResponseEntity<HLSimulationEntity> getMonthlyPayment(@PathVariable Long id) {
        HLSimulationEntity hlSimulationUpdated = hLSimulationService.monthlyPaymentCalc(id);
        return ResponseEntity.ok(hlSimulationUpdated);
    }

    */
}
