package com.example.farming.controller;

import com.example.farming.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/usines")
public class UsineController {
    private final FactoryService factoryService;

    @Autowired
    public UsineController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<Void> processFactory(@PathVariable UUID id) {
        factoryService.processFactory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/can-process")
    public ResponseEntity<Boolean> canProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(factoryService.canProcess(id));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Void> pauseFactory(@PathVariable UUID id) {
        factoryService.pauseFactory(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<Void> resumeFactory(@PathVariable UUID id) {
        factoryService.resumeFactory(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process-all")
    public ResponseEntity<Void> processAllFactories() {
        factoryService.processAllFactories();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/process-bakery")
    public ResponseEntity<Void> processBakery(@PathVariable UUID id) {
        factoryService.processBakery(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/process-chocolate")
    public ResponseEntity<Void> processChocolateFactory(@PathVariable UUID id) {
        factoryService.processChocolateFactory(id);
        return ResponseEntity.ok().build();
    }
} 