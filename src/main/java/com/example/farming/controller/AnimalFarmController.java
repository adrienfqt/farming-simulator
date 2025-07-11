package com.example.farming.controller;

import com.example.farming.entity.TypeAnimal;
import com.example.farming.service.AnimalFarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/animal-farms")
public class AnimalFarmController {
    private final AnimalFarmService animalFarmService;

    @Autowired
    public AnimalFarmController(AnimalFarmService animalFarmService) {
        this.animalFarmService = animalFarmService;
    }

    @PostMapping("/{farmId}/feed/{animalId}")
    public ResponseEntity<Void> feedAnimal(@PathVariable UUID farmId, @PathVariable UUID animalId, @RequestParam double grassAmount) {
        animalFarmService.feedAnimal(animalId, grassAmount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{farmId}/process")
    public ResponseEntity<Void> processProduction(@PathVariable UUID farmId) {
        animalFarmService.processProduction(farmId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{farmId}/remove-dead")
    public ResponseEntity<Void> removeDeadAnimals(@PathVariable UUID farmId) {
        animalFarmService.removeDeadAnimals(farmId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{farmId}/add-animal")
    public ResponseEntity<Void> addAnimal(@PathVariable UUID farmId, @RequestParam TypeAnimal type, @RequestParam String nom) {
        animalFarmService.addAnimal(farmId, type, nom);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process-all")
    public ResponseEntity<Void> processAllFarms() {
        animalFarmService.processAllFarms();
        return ResponseEntity.ok().build();
    }
} 