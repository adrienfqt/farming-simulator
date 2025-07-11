package com.example.farming.service;

import com.example.farming.entity.*;
import com.example.farming.repository.FermeAnimaliereRepository;
import com.example.farming.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalFarmServiceImpl implements AnimalFarmService {
    private final FermeAnimaliereRepository farmRepository;
    private final AnimalRepository animalRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void feedAnimal(UUID animalId, double grassAmount) {
        Animal animal = animalRepository.findById(animalId).orElseThrow();
        animal.setStockHerbe(animal.getStockHerbe() + grassAmount);
        animalRepository.save(animal);
    }

    @Override
    @Transactional
    public void processProduction(UUID farmId) {
        FermeAnimaliere farm = farmRepository.findById(farmId).orElseThrow();
        for (Animal animal : farm.getAnimaux()) {
            if (!animal.isVivant()) continue;
            double consommationEau = getWaterConsumption(animal.getType());
            double consommationHerbe = getGrassConsumption(animal.getType());
            if (storageService.getContents(TypeStockage.EAU).getOrDefault("eau", 0) < consommationEau || animal.getStockHerbe() < consommationHerbe) {
                animal.setStockHerbe(animal.getStockHerbe() - consommationHerbe);
                if (animal.getStockHerbe() < -5) {
                    animal.setVivant(false);
                }
                animalRepository.save(animal);
                continue;
            }
            storageService.removeProduct("eau", (int) consommationEau, TypeStockage.EAU);
            animal.setStockHerbe(animal.getStockHerbe() - consommationHerbe);
            double[] prod = getProduction(animal.getType());
            if (prod[0] > 0) storageService.addProduct(getProductName(animal.getType()), (int) prod[0], TypeStockage.DEFAULT);
            if (prod[1] > 0) storageService.addProduct("fumier", (int) prod[1], TypeStockage.DEFAULT);
            animal.setProductionAccumulee(animal.getProductionAccumulee() + prod[0]);
            animal.setProductionFumier(animal.getProductionFumier() + prod[1]);
            animalRepository.save(animal);
        }
    }

    @Override
    @Transactional
    public void removeDeadAnimals(UUID farmId) {
        FermeAnimaliere farm = farmRepository.findById(farmId).orElseThrow();
        farm.getAnimaux().removeIf(animal -> !animal.isVivant());
        farmRepository.save(farm);
    }

    @Override
    @Transactional
    public void addAnimal(UUID farmId, TypeAnimal type, String nom) {
        FermeAnimaliere farm = farmRepository.findById(farmId).orElseThrow();
        if (farm.getAnimaux().size() >= 10) throw new IllegalStateException("Max 10 animaux par ferme");
        Animal animal = new Animal();
        animal.setType(type);
        animal.setVivant(true);
        animal.setStockHerbe(10);
        animal.setNom(nom);
        animal.setDateEntreeFerme(java.time.LocalDateTime.now());
        animalRepository.save(animal);
        farm.getAnimaux().add(animal);
        farmRepository.save(farm);
    }

    @Override
    @Transactional
    public void processAllFarms() {
        farmRepository.findAll().forEach(farm -> processProduction(farm.getId()));
    }

    @Override
    @Transactional
    public void processCowFarm(UUID farmId) {
        processProductionByType(farmId, TypeAnimal.VACHE);
    }

    @Override
    @Transactional
    public void processSheepFarm(UUID farmId) {
        processProductionByType(farmId, TypeAnimal.MOUTON);
    }

    @Override
    @Transactional
    public void processChickenFarm(UUID farmId) {
        processProductionByType(farmId, TypeAnimal.POULE);
    }

    private void processProductionByType(UUID farmId, TypeAnimal type) {
        FermeAnimaliere farm = farmRepository.findById(farmId).orElseThrow();
        for (Animal animal : farm.getAnimaux()) {
            if (animal.getType() == type && animal.isVivant()) {
                processProduction(farmId);
            }
        }
    }

    private double getWaterConsumption(TypeAnimal type) {
        return switch (type) {
            case VACHE -> 3;
            case MOUTON -> 2;
            case POULE -> 1;
        };
    }
    private double getGrassConsumption(TypeAnimal type) {
        return switch (type) {
            case VACHE -> 3;
            case MOUTON -> 2;
            case POULE -> 1;
        };
    }
    private double[] getProduction(TypeAnimal type) {
        return switch (type) {
            case VACHE -> new double[]{20, 5};
            case MOUTON -> new double[]{5, 5};
            case POULE -> new double[]{1, 0};
        };
    }
    private String getProductName(TypeAnimal type) {
        return switch (type) {
            case VACHE -> "lait";
            case MOUTON -> "laine";
            case POULE -> "oeufs";
        };
    }
} 