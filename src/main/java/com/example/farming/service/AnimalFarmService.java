package com.example.farming.service;

import com.example.farming.entity.TypeAnimal;
import java.util.UUID;

public interface AnimalFarmService {
    void feedAnimal(UUID animalId, double grassAmount);
    void processProduction(UUID farmId);
    void removeDeadAnimals(UUID farmId);
    void addAnimal(UUID farmId, TypeAnimal type, String nom);
    void processAllFarms();
    void processCowFarm(UUID farmId);
    void processSheepFarm(UUID farmId);
    void processChickenFarm(UUID farmId);
} 