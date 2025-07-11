package com.example.farming.service;

import com.example.farming.entity.Serre;
import com.example.farming.entity.TypeStockage;
import com.example.farming.repository.SerreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GreenhouseServiceImpl implements GreenhouseService {
    private final SerreRepository serreRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void processGreenhouse(UUID greenhouseId) {
        Serre serre = serreRepository.findById(greenhouseId).orElseThrow();
        if (serre.isEnPause()) return;
        if (!canProcess(greenhouseId)) {
            pauseGreenhouse(greenhouseId);
            return;
        }
        if (storageService.isFull(TypeStockage.DEFAULT)) {
            pauseGreenhouse(greenhouseId);
            return;
        }
        storageService.removeProduct("eau", (int) serre.getConsommationEau(), TypeStockage.EAU);
        storageService.addProduct(serre.getProduit(), serre.getCapaciteParCycle(), TypeStockage.DEFAULT);
        serre.setEnPause(false);
        serreRepository.save(serre);
    }

    @Override
    public boolean canProcess(UUID greenhouseId) {
        Serre serre = serreRepository.findById(greenhouseId).orElseThrow();
        Map<String, Integer> stock = storageService.getContents(TypeStockage.EAU);
        return stock.getOrDefault("eau", 0) >= serre.getConsommationEau();
    }

    @Override
    @Transactional
    public void pauseGreenhouse(UUID greenhouseId) {
        Serre serre = serreRepository.findById(greenhouseId).orElseThrow();
        serre.setEnPause(true);
        serreRepository.save(serre);
    }

    @Override
    @Transactional
    public void resumeGreenhouse(UUID greenhouseId) {
        Serre serre = serreRepository.findById(greenhouseId).orElseThrow();
        serre.setEnPause(false);
        serreRepository.save(serre);
    }

    @Override
    @Transactional
    public void processAllGreenhouses() {
        serreRepository.findAll().forEach(serre -> {
            if (!serre.isEnPause()) {
                processGreenhouse(serre.getId());
            }
        });
    }
} 