package com.example.farming.controller;

import com.example.farming.service.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/serres")
public class SerreController {
    private final GreenhouseService greenhouseService;

    @Autowired
    public SerreController(GreenhouseService greenhouseService) {
        this.greenhouseService = greenhouseService;
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<Void> processGreenhouse(@PathVariable UUID id) {
        greenhouseService.processGreenhouse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/can-process")
    public ResponseEntity<Boolean> canProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(greenhouseService.canProcess(id));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Void> pauseGreenhouse(@PathVariable UUID id) {
        greenhouseService.pauseGreenhouse(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<Void> resumeGreenhouse(@PathVariable UUID id) {
        greenhouseService.resumeGreenhouse(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process-all")
    public ResponseEntity<Void> processAllGreenhouses() {
        greenhouseService.processAllGreenhouses();
        return ResponseEntity.ok().build();
    }
} 