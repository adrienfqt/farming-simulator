package com.example.farming.controller;

import com.example.farming.entity.TypeMachine;
import com.example.farming.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/machines")
public class MachineController {
    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> isAvailable(@RequestParam TypeMachine type, @RequestParam(required = false) String specialization) {
        return ResponseEntity.ok(machineService.isAvailable(type, specialization));
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignMachine(@RequestParam TypeMachine type, @RequestParam(required = false) String specialization) {
        machineService.assignMachine(type, specialization);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/release")
    public ResponseEntity<Void> releaseMachine(@RequestParam TypeMachine type, @RequestParam(required = false) String specialization) {
        machineService.releaseMachine(type, specialization);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAvailableCount(@RequestParam TypeMachine type, @RequestParam(required = false) String specialization) {
        return ResponseEntity.ok(machineService.getAvailableCount(type, specialization));
    }

    @GetMapping("/specialization")
    public ResponseEntity<Optional<String>> getSpecializationForMachine(@RequestParam TypeMachine type) {
        return ResponseEntity.ok(machineService.getSpecializationForMachine(type));
    }
} 