package com.example.farming.service;

import com.example.farming.entity.Stockage;
import com.example.farming.entity.TypeStockage;
import com.example.farming.repository.StockageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class StorageServiceImpl implements StorageService {
    private final StockageRepository stockageRepository;

    @Autowired
    public StorageServiceImpl(StockageRepository stockageRepository) {
        this.stockageRepository = stockageRepository;
    }

    private Stockage getStockage(TypeStockage type) {
        return stockageRepository.findAll().stream()
                .filter(s -> s.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Stockage non trouvé pour le type " + type));
    }

    @Override
    @Transactional
    public void addProduct(String product, int quantity, TypeStockage type) {
        Stockage stockage = getStockage(type);
        int currentTotal = stockage.getContenu().values().stream().mapToInt(Integer::intValue).sum();
        if (currentTotal + quantity > stockage.getCapaciteMax()) {
            throw new IllegalStateException("Stockage plein pour le type " + type);
        }
        stockage.getContenu().merge(product, quantity, Integer::sum);
        stockageRepository.save(stockage);
    }

    @Override
    @Transactional
    public void removeProduct(String product, int quantity, TypeStockage type) {
        Stockage stockage = getStockage(type);
        int current = stockage.getContenu().getOrDefault(product, 0);
        if (current < quantity) {
            throw new IllegalStateException("Quantité insuffisante de " + product + " dans le stockage " + type);
        }
        stockage.getContenu().put(product, current - quantity);
        if (stockage.getContenu().get(product) == 0) {
            stockage.getContenu().remove(product);
        }
        stockageRepository.save(stockage);
    }

    @Override
    public boolean isFull(TypeStockage type) {
        Stockage stockage = getStockage(type);
        int currentTotal = stockage.getContenu().values().stream().mapToInt(Integer::intValue).sum();
        return currentTotal >= stockage.getCapaciteMax();
    }

    @Override
    public int getAvailableSpace(TypeStockage type) {
        Stockage stockage = getStockage(type);
        int currentTotal = stockage.getContenu().values().stream().mapToInt(Integer::intValue).sum();
        return stockage.getCapaciteMax() - currentTotal;
    }

    @Override
    public Map<String, Integer> getContents(TypeStockage type) {
        Stockage stockage = getStockage(type);
        return new HashMap<>(stockage.getContenu());
    }

    @Override
    @Transactional
    public void clearProduct(String product, TypeStockage type) {
        Stockage stockage = getStockage(type);
        stockage.getContenu().remove(product);
        stockageRepository.save(stockage);
    }
}
