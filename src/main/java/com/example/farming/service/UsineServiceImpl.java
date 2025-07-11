package com.example.farming.service;

import com.example.farming.entity.Usine;
import com.example.farming.entity.TypeUsine;
import com.example.farming.entity.TypeStockage;
import com.example.farming.repository.UsineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.UUID;

@Service
public class FactoryServiceImpl implements FactoryService {
    private final UsineRepository usineRepository;
    private final StorageService storageService;

    @Autowired
    public FactoryServiceImpl(UsineRepository usineRepository, StorageService storageService) {
        this.usineRepository = usineRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public void processFactory(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        if (usine.isEnPause()) return;
        if (!canProcess(factoryId)) {
            pauseFactory(factoryId);
            return;
        }
        if (storageService.isFull(TypeStockage.DEFAULT)) {
            pauseFactory(factoryId);
            return;
        }
        for (Map.Entry<String, Integer> entry : usine.getIntrants().entrySet()) {
            storageService.removeProduct(entry.getKey(), entry.getValue(), TypeStockage.DEFAULT);
        }
        int totalInput = usine.getIntrants().values().stream().mapToInt(Integer::intValue).sum();
        int output = totalInput * usine.getMultiplicateur();
        storageService.addProduct(usine.getProduitFinal(), output, TypeStockage.DEFAULT);
        usine.setEnPause(false);
        usineRepository.save(usine);
    }

    @Override
    public boolean canProcess(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        for (Map.Entry<String, Integer> entry : usine.getIntrants().entrySet()) {
            Map<String, Integer> stock = storageService.getContents(TypeStockage.DEFAULT);
            if (stock.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void pauseFactory(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        usine.setEnPause(true);
        usineRepository.save(usine);
    }

    @Override
    @Transactional
    public void resumeFactory(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        usine.setEnPause(false);
        usineRepository.save(usine);
    }

    @Override
    @Transactional
    public void processAllFactories() {
        usineRepository.findAll().forEach(usine -> {
            if (!usine.isEnPause()) {
                processFactory(usine.getId());
            }
        });
    }

    @Override
    @Transactional
    public void processBakery(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        if (usine.isEnPause()) return;
        // Liste des intrants pour la boulangerie
        String[] intrants = {"sucre", "lait_sterilise", "farine", "oeufs", "beurre", "chocolat", "fraises"};
        int min = Integer.MAX_VALUE;
        Map<String, Integer> stock = storageService.getContents(TypeStockage.DEFAULT);
        for (String intrant : intrants) {
            min = Math.min(min, stock.getOrDefault(intrant, 0));
        }
        if (min == 0 || min == Integer.MAX_VALUE) {
            pauseFactory(factoryId);
            return;
        }
        int output = min * usine.getMultiplicateur();
        for (String intrant : intrants) {
            storageService.removeProduct(intrant, min, TypeStockage.DEFAULT);
        }
        storageService.addProduct(usine.getProduitFinal(), output, TypeStockage.DEFAULT);
        usine.setEnPause(false);
        usineRepository.save(usine);
    }

    @Override
    @Transactional
    public void processChocolateFactory(UUID factoryId) {
        Usine usine = usineRepository.findById(factoryId).orElseThrow();
        if (usine.isEnPause()) return;
        Map<String, Integer> stock = storageService.getContents(TypeStockage.DEFAULT);
        int cacao = stock.getOrDefault("cacao", 0);
        int sucre = stock.getOrDefault("sucre", 0);
        int lait = stock.getOrDefault("lait", 0);
        int min = Math.min(cacao, Math.min(sucre, lait));
        if (min < 100) {
            pauseFactory(factoryId);
            return;
        }
        int cycles = min / 100;
        int output = cycles * 600; // 100L de chaque -> 600L chocolat
        storageService.removeProduct("cacao", cycles * 100, TypeStockage.DEFAULT);
        storageService.removeProduct("sucre", cycles * 100, TypeStockage.DEFAULT);
        storageService.removeProduct("lait", cycles * 100, TypeStockage.DEFAULT);
        storageService.addProduct(usine.getProduitFinal(), output, TypeStockage.DEFAULT);
        usine.setEnPause(false);
        usineRepository.save(usine);
    }
}
