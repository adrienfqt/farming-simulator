package com.example.farming.controller;

import com.example.farming.entity.TypeCulture;
import com.example.farming.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/fields")
public class FieldController {
    private final FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping("/{id}/plow")
    public ResponseEntity<Void> plow(@PathVariable UUID id) {
        fieldService.plow(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sow")
    public ResponseEntity<Void> sow(@PathVariable UUID id, @RequestParam TypeCulture cropType) {
        fieldService.sow(id, cropType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/fertilize")
    public ResponseEntity<Void> fertilize(@PathVariable UUID id) {
        fieldService.fertilize(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/harvest")
    public ResponseEntity<Void> harvest(@PathVariable UUID id) {
        fieldService.harvest(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/lot/{lotId}")
    public ResponseEntity<Void> assignLot(@PathVariable UUID id, @PathVariable UUID lotId) {
        fieldService.assignLot(id, lotId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-states")
    public ResponseEntity<Void> updateFieldStates() {
        fieldService.updateFieldStates();
        return ResponseEntity.ok().build();
    }
} 